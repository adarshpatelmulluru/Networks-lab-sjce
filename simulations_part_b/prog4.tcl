#creating a new simulator
set ns [new Simulator]

#creating topograpphy
set topo [new Topography]
$topo load_flatgrid 1000 1000

#setting trace file and namfile variables

set tracefile [open prog4.tr w]
$ns trace-all $tracefile
set namfile [open prog4.nam w]
$ns namtrace-all $namfile
$ns namtrace-all-wireless $namfile 1000 1000

#writing the finish procedure

proc finish {} {
global ns tracefile namfile #using above variables
$ns flush-trace
close $tracefile
close $namfile
exec nam prog4.nam &
exec echo "the number of packets dropped are:" &
exec grep -c "^d" prog4.tr &
exit 0
}

#setting up the mobile adhoc router

$ns node-config -adhocRouting DSDV \
-llType LL \
-macType Mac/802_11 \
-ifqType Queue/DropTail \
-ifqLen 15 \
-phyType Phy/WirelessPhy \
-channelType Channel/WirelessChannel \
-propType Propagation/TwoRayGround \
-antType Antenna/OmniAntenna \
-topoInstance $topo \
-agentTrace ON \
-routerTrace ON

#creating nodes
create-god 6
set n0 [$ns node]
$n0 set X_ 873
$n0 set Y_ 521
$n0 set Z_ 0.0
$ns initial_node_pos $n0 20
set n1 [$ns node]
$n1 set X_ 234
$n1 set Y_ 741
$n1 set Z_ 0.0
$ns initial_node_pos $n1 20
set n2 [$ns node]
$n2 set X_ 763
$n2 set Y_ 338
$n2 set Z_ 0.0
$ns initial_node_pos $n2 20
set n3 [$ns node]
$n3 set X_ 291
$n3 set Y_ 199
$n3 set Z_ 0.0
$ns initial_node_pos $n3 20
set n4 [$ns node]
$n4 set X_ 539
$n4 set Y_ 188
$n4 set Z_ 0.0
$ns initial_node_pos $n4 20
set n5 [$ns node]
$n5 set X_ 680
$n5 set Y_ 170
$n5 set Z_ 0.0
$ns initial_node_pos $n5 20

set udp0 [new Agent/UDP]
$udp0 set packetSize_ 1000
$ns attach-agent $n0 $udp0
set null0 [new Agent/Null]
$ns attach-agent $n5 $null0
$ns connect $udp0 $null0

set tcp0 [new Agent/TCP]
$ns attach-agent $n1 $tcp0
set sink0 [new Agent/TCPSink]
$ns attach-agent $n4 $sink0
$ns connect $tcp0 $sink0

set cbr0 [new Application/Traffic/CBR]
$cbr0 set type_ CBR
$cbr0 set packetSize_ 100
$cbr0 set rate_ 0.8Mb
$cbr0 set random_ null
$cbr0 attach-agent $udp0

set ftp0 [new Application/FTP]
$ftp0 set type_ FTP
$ftp0 attach-agent $tcp0


#schedule the events 

$ns at 0.0 "$ftp0 start"
$ns at 0.2 "$cbr0 start"
$ns at 8.4 "$n3 setdest 100 200 20"
$ns at 10.2 "$n4 setdest 200 300 20"
$ns at 12.8 "$ftp0 stop"
$ns at 13.0 "$cbr0 stop"
$ns at 14.0 "finish"

$ns run
