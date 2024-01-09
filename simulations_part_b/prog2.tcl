#creating a new simulator
set ns [new Simulator]

#set trace file and nam file variables
set tracefile [open prog2.tr w]
$ns trace-all $tracefile
set namfile [open prog2.nam w]
$ns namtrace-all $namfile

$ns color 1 blue
$ns color 2 green

#write the  procedure finish

proc finish {} {
global ns tracefile namfile
$ns flush-trace
close $tracefile
close $namfile
exec nam prog2.nam &
exec echo "no of ping packets dropped are " &
#exec grep -c "^d" prog2.tr | cut -d " " -f 5 | grep -c "ping" &
puts "The number of ping packets dropped are: [exec grep -c "^d" prog2.tr]"
exit 0
}

#creating 6 nodes to form a network

for {set i 0} {$i<6} {incr i} {
set n($i) [$ns node]
}

for {set j 0} {$j<5} {incr j} {
$ns duplex-link $n($j) $n([expr ($j+1)]) 0.5Mb 9ms DropTail
}

#writing a instance procedure for recv
Agent/Ping instproc recv {from rtt} {
$self instvar node_
puts "node [$node_ id] pinged message from node $from with round trip time $rtt ms"
}

#creating ping agents for node 0 and node 5
set p0 [new Agent/Ping]
$p0 set class_ 1
$ns attach-agent $n(0) $p0 
set p1 [new Agent/Ping]
$p1 set class_ 1
$ns attach-agent $n(5) $p1
$ns connect $p0 $p1

#queue limit and positioing of nodes 3 and 4
$ns queue-limit $n(3) $n(4) 2
$ns duplex-link-op $n(3) $n(4) queuePos 0.25

#creating tcp agents for nodes 3 and 4 
set tcp0 [new Agent/TCP]
$tcp0 set class_ 2 #downgrading class such that we overload the nodes
$ns attach-agent $n(3) $tcp0
set sink0 [new Agent/TCPSink]
$sink0 set class_ 2
$ns attach-agent $n(4) $sink0
$ns connect $tcp0 $sink0

#creating trafiic agent CBR
set cbr0 [new Application/Traffic/CBR]
$cbr0 set packetSize_ 100
$cbr0 set rate_ 1Mb
$cbr0 attach-agent $tcp0

#scheduling different events

$ns at 0.0 "$p0 send"
$ns at 0.4 "$p1 send"
$ns at 0.4 "$cbr0 start"
$ns at 0.6 "$p0 send"
$ns at 0.7 "$p1 send"

$ns at 0.8 "$p0 send"
#$ns at 0.9 "$p1 send"
#$ns at 1.6 "$p0 send"
#$ns at 1.8 "$p1 send"
$ns at 8.8 "$cbr0 stop"
#$ns at 6.0 "$p0 send"
#$ns at 6.2 "$p1 send"
$ns at 10.0 "finish"

$ns run

