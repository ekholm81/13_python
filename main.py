# coding: utf-8
import os
import time
from Tips import Tips
from Jaromir import Jaromir
def main():
	user="mq"
	f="Log--"+time.ctime()
	logfile=open("/home/"+user+"/13_python/"+f,'a')
	topptips=Tips("topptipset", user)
	topptips.get_data()
	tt=topptips.get_closing()
	powerplay=Tips("powerplay", user)
	powerplay.get_data()
	pp=powerplay.get_closing()
	while True:
		if topptips.get_closing()!=tt and topptips.internetERROR==False:
			logfile.write("-----------------"+time.ctime()+"topptipsupdate\n")
			topptips.log(tt)
			tt=topptips.get_closing()		
		topptips.get_data()
		
		if topptips.internetERROR==False:
			if topptips.zeroodds==True:
				logfile.write("Jaromir:TT - zero odds spotted!\n")
			else:
				logfile.write("Jaromir:TT - "+repr(Jaromir(topptips.ncrossed, topptips.nodds, topptips.oms).medel)+"\n")
		else:
			logfile.write("Jaromir:TT - InternetERROR spotted!\n")
			
			
		if powerplay.get_closing()!=pp and powerplay.internetERROR==False:
			logfile.write("-----------------"+time.ctime()+"powerplayupdate\n")
			powerplay.log(pp)
			pp=powerplay.get_closing()
		powerplay.get_data()
		
		if powerplay.internetERROR==False:
			if powerplay.zeroodds==True:
				logfile.write("Jaromir:PP - zero odds spotted!\n")
			else:
				logfile.write("Jaromir:PP - "+repr(Jaromir(powerplay.ncrossed, powerplay.nodds, powerplay.oms).medel)+"\n")
		else:
			logfile.write("Jaromir:PP - InternetERROR spotted!\n")
		logfile.write(time.ctime()+" waiting...\n\n")
		logfile.close()
		time.sleep(1)
		logfile=open(f,'a')
main()	

