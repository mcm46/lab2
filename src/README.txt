################
# Team Info
################

Name1: Patrick Pensabene 
NetId1: pcp2

Name2: Tim Gornik
NetId2: tmg20

Name3: Chris Muto
NetId3: mcm46

###############
# Time spent
###############

Together we probably spent about 12 hours. We wrote most of our code during team coding sessions.

################
# Files to submit
################

*.java  #Including the test case files; Please do not submit tar or zip files
README	#This file filled with the lab implementation details
Elevator.log #The name of the log file you generate should be Elevator.log.
	 # You can submit the log file along with your source code. However, 
	 # it should be generated automatically when the Elevator is executed.

####################################
# Implementation details
####################################

For testing purposes run Main.java and the output will be printed straight to the console. To change the number of floors or the number of elevators
change the fields at the top of the Building class. The people that use these elevators take a queue with the floors that they will request. This is set
up in Main.java.

For our design choice, we used a lockObject to lock on instead of implementing a busy wait. This was done in both the EventBarrier
and the Elevator class. We also decided to have the callUp and callDown methods of the Building class return the elevator that was assigned. This
made it very simple for the person waiting to enter the elevator since it was given the instance once the method returned.


####################################
# Feedback on the lab
####################################

The lab was a lot more fun than the last one, although it was a pain to debug. In the end we do feel that we know a lot more
about concurrency than we did previously.

##################################
# Additional comments
##################################

