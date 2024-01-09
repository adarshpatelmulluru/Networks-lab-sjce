#create a network simulator

set ns [new Simulator]

set tracefile [open prog3.tr w]
$ns trace-all $tracefile
set namfile [open prog3.nam w]
$ns namtrace-all $namfile

$ns color 1 blue
$ns color 2 brown

set graph0 [open graph0 w]
set graph1 [open graph1 w]


proc finish {} {
global ns namfile tracefile
$ns flush-trace
close $tracefile
close $namfile
exec nam prog3.nam &
exec xgraph graph0 graph1 &
exit 0
}

proc plotgraph {tcpsource filename} {
global ns
set time 0.1
set now [$ns now]
set cwnd [$tcpsource set cwnd_]
puts $filename "$now $cwnd"
$ns at [expr $time+$now] "plotgraph $tcpsource $filename"
}

#create nodes
for {set i 0} {$i<6} {incr i} {
set n($i) [$ns node]
}

$ns duplex-link $n(0) $n(2) 1Mb 10ms DropTail
$ns duplex-link $n(1) $n(2) 1Mb 10ms DropTail
$ns duplex-link $n(2) $n(3) 0.5Mb 8ms DropTail

set lan [$ns newLan "$n(3) $n(4) $n(5)" 0.5Mb 5ms LL Queue/DropTail MAC/802_3 Channel]

# set orientation

$ns duplex-link-op $n(0) $n(2) orient right-down
$ns duplex-link-op $n(1) $n(2) orient right-up
$ns duplex-link-op $n(2) $n(3) orient right

#set queue limit to node 2 to node 3

$ns queue-limit $n(2) $n(3) 10
$ns duplex-link-op $n(2) $n(3) queuePos 0.9 

#creating two tcp agents and ftp application

set tcp0 [new Agent/TCP/Newreno]
$tcp0 set fid_ 1
$tcp0 set window_ 5000
$tcp0 set packetSize_ 120
$ns attach-agent $n(0) $tcp0
set sink0 [new Agent/TCPSink/DelAck]
$ns attach-agent $n(4) $sink0
$ns connect $tcp0 $sink0

set ftp0 [new Application/FTP]
$ftp0 set type_ FTP
$ftp0 attach-agent $tcp0

set tcp1 [new Agent/TCP/Newreno]
$tcp1 set fid_ 2
$tcp1 set window_ 5000
$tcp1 set packetSize_ 120
$ns attach-agent $n(1) $tcp1
set sink1 [new Agent/TCPSink/DelAck]
$ns attach-agent $n(5) $sink1
$ns connect $tcp1 $sink1

set ftp1 [new Application/FTP]
$ftp1 set type_ FTP
$ftp1 attach-agent $tcp1

#schedule the events

$ns at 0.0 "$ftp0 start"
$ns at 0.0 "plotgraph $tcp0 $graph0"
$ns at 2.0 "$ftp1 start"
$ns at 2.0 "plotgraph $tcp1 $graph1"
$ns at 20.0 "$ftp0 stop"
$ns at 22.0 "$ftp1 stop"
$ns at 22.0 "finish"

$ns run 

