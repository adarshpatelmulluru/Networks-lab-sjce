#create a new simulator
set ns [new Simulator]

#set trace and namfile variables
set tracefile [open prog1.tr w]
$ns trace-all $tracefile
set namfile [open prog1.nam w]
$ns namtrace-all $namfile


#writing procedure finish{}

proc finish {} {
global ns tracefile namfile
$ns flush-trace
close $tracefile
close $namfile
exec nam prog1.nam &
#set drop_count [string trim[exec grep -c "^d" prog1.tr] 0-9]
#puts "the number of packets dropped are: $drop_count"
puts "The number of packet drops is [exec grep -c "^d" prog1.tr]"
#exec grep -c "^d" prog1.tr &  #-c counts the number of times "^d" occurs in prog1.tr
exit 0
}

$ns color 1 blue

#creating 3 nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]

$n0 label "tcp source"
$n2 label "tcpp sink"

$ns duplex-link $n0 $n1 1Mb 4ms DropTail #bandwidth and delay can varied for varying papckets dropped
$ns duplex-link $n1 $n2 1Mb 4ms DropTail

$ns duplex-link-op $n0 $n1 orient left
$ns duplex-link-op $n1 $n2 orient left
# queue-limit can be varied to vary packets dropped
$ns queue-limit $n0 $n1 10
$ns queue-limit $n1 $n2 10

#creating tcpp agents
set tcp0 [new Agent/TCP]
$ns attach-agent $n0 $tcp0
set sink0 [new Agent/TCPSink]
$ns attach-agent $n2 $sink0
$ns connect $tcp0 $sink0

#creating traffic controller
set cbr0 [new Application/Traffic/CBR]
#$cbr0 set type_ CBR
$cbr0 set packetSize_ 80
$cbr0 set rate_ 1Mb
#$cbr0 set random_ false
$cbr0 attach-agent $tcp0
$tcp0 set class_ 1


#scheduling different procedures
$ns at 0.0 "$cbr0 start"
$ns at 4.8 "$cbr0 stop"
$ns at 5.0 "finish"

$ns run

