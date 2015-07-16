In order to compile for this sensorboard you should install the "epic" platform from the tinyos repository!
So you shoud download from the tinyos repository the 

tinyos-2.x-contrib/berkeley/acme/tos/sensorboards

directory and copy its "ACmeX2" subdirectory in tinyos-2.x/tos/sensorboards

Now, you should open the “epic.target” file located in “tinyos-2.x/support/make” and add in this file the following line:

SENSORBOARD ?= ACmeX2


