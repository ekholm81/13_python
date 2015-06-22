# coding: utf-8
import os
import time
from Tips import Tips
from Jaromir import Jaromir
from Mailer import Mailer
import matplotlib.pyplot as plt
def main():
	m=Mailer()
	limit=0
	user="mq"
	f="Log--"+time.ctime()
	logfile=open("/home/"+user+"/13_python/Log",'a')
	topptips=Tips("topptipset", user)
	topptips.get_data()
	tt=topptips.get_closing()
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
				j=Jaromir(topptips.ncrossed, topptips.nodds, topptips.oms)
				j.get_medel()
				Jaromir(topptips.ncrossed, topptips.nodds, topptips.oms).get_medel()
				logfile.write("Jaromir:TT - EXP(500): "+repr(j.exp)+" Chance: "+repr(j.s)+"%\n")
				if j.exp[0]>limit and m.ready()==True:
					#print tt
					m.send_mail(tt,j.exp,j.s)
		else:
			logfile.write("Jaromir:TT - InternetERROR spotted!\n")
		logfile.write("sleep "+time.ctime()+"\n")	
		logfile.close()
		
		time.sleep(60)
		logfile=open(f,'a')
main()	

