# COMP250 Assignment 2 DEBUGGER - McGill University Winter 2020

# IMPORANT! Install Instructions -- SOME ASSEMBLY REQUIRED

For IntelliJ IDEA (recommended)

VCS -> Get from Version Control... -> Paste the URL of this repository

For Eclipse

File -> Import -> Git -> Projects from Git (With Smart Import) -> Clone URI -> paste URL of this repository into the URI box -> Click next a bunch, setting directory at your own discression, Master branch from origin. All else default -> Finish

For both:

Drag your .java files from the assignment into the tester package.

In case your IDE does not automatically set package name, change your package name to COMP250A1_W2020

Regularly update the tester (pull the repository). Your assignment files will be ignored.

#IMPORTANT: ASSEMBLY REQUIRED:

Paste the follwing line before the while loop inside of travel, where curLine and curStation are defined.

<code>TrainVisualizer t = new TrainVisualizer(this); 	t.paint(null);</code>

Paste the following line as the first line inside of the while loop:

<code>t.delay(500); t.passCurrent(curStation); t.repaint();</code>

# How to Run:

Run any tester (I supply SashaTrainRide.java), view the visualizer.

# NOTE:
You are expected to have your own classes written as assigned which pass the Syntax Tester for this to run.

This repository will ignore correctly named class files from the assignment, so pulling it to your project will have no effect on existing code.

If you wish to collaborate, or have any questions contact me at sasha@sashaphoto.ca


