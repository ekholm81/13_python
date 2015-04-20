# coding: utf-8
import os
import time
from Tips import Tips
from Jaromir import Jaromir
def main():
	f="Log--"+time.ctime()
	logfile=open(f,'a')
	topptips=Tips("topptipset", "mq")
	topptips.get_data()
	j=Jaromir(topptips.ncrossed, topptips.nodds, topptips.oms)
	print j.medel
	print j.sane
	tt=topptips.get_closing()
	powerplay=Tips("powerplay", "mq")
	powerplay.get_data()
	pp=powerplay.get_closing()
	while True:
		if topptips.get_closing()!=tt:
			logfile.write("-----------------"+time.ctime()+"topptipsupdate\n")
			topptips.log(tt)
			tt=topptips.get_closing()		
		topptips.get_data()
		if powerplay.get_closing()!=pp:
			logfile.write("-----------------"+time.ctime()+"powerplayupdate\n")
			powerplay.log(pp)
			pp=powerplay.get_closing()
		powerplay.get_data()
		logfile.write(time.ctime()+" waiting...\n")
		logfile.close()
		time.sleep(300)
		logfile=open(f,'a')
main()	

