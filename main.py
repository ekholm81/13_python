# coding: utf-8
import os
import time
from Tips import Tips
def main():
	topptips=Tips("topptipset", "mq")
	topptips.get_html()
	topptips.get_data()
	tt=topptips.get_closing()
	powerplay=Tips("powerplay", "mq")
	powerplay.get_html()
	powerplay.get_data()
	pp=powerplay.get_closing()
	print tt
	while True:
	
		topptips.get_html()
		if topptips.get_closing()!=tt:
			print time.ctime()+"topptipsupdate"
			topptips.log()
			tt=topptips.get_closing()		
		topptips.get_data()
		
		powerplay=Tips("powerplay", "mq")
		powerplay.get_html() 
		if powerplay.get_closing()!=pp:
			print time.ctime()+"powerplayupdate"
			powerplay.log()
			pp=powerplay.get_closing()
		powerplay.get_data()
		
		print time.ctime()+" waiting..."
		time.sleep(1)
main()	

