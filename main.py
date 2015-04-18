# coding: utf-8
import os
import time
from Tips import Tips
def main():
	file=time.ctime()+"-log"
	logfile=open(file,'a')
	topptips=Tips("topptipset", "pi")
	topptips.get_html()
	topptips.get_data()
	tt=topptips.get_closing()
	powerplay=Tips("powerplay", "pi")
	powerplay.get_html()
	powerplay.get_data()
	pp=powerplay.get_closing()
	while True:
		topptips.get_html()
		if topptips.get_closing()!=tt:
			logfile.write("-----------------"+time.ctime()+"topptipsupdate\n")
			topptips.log(tt)
			tt=topptips.get_closing()		
		topptips.get_data()
		powerplay.get_html() 
		if powerplay.get_closing()!=pp:
			logfile.write("-----------------"+time.ctime()+"powerplayupdate\n")
			powerplay.log(pp)
			pp=powerplay.get_closing()
		powerplay.get_data()
		logfile.write(time.ctime()+" waiting...\n")
		logfile.close()
		time.sleep(300)
		logfile=open(file,'a')
main()	

