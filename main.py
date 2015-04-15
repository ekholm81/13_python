# coding: utf-8
import os
import time
from Tips import Tips
def main():
	topptips=Tips("topptipset", "mq")
	topptips.get_html()
	topptips.get_data()
	topptips.erase_html()
	tt=topptips.closing
	powerplay=Tips("powerplay", "mq")
	powerplay.get_html()
	powerplay.get_data()
	powerplay.erase_html()
	pp=powerplay.closing
	while True:
		topptips=Tips("topptipset", "mq")
		topptips.get_html()
		topptips.get_data()
		topptips.erase_html()
		if topptips.closing!=tt:
			print time.ctime()+"topptipsupdate"
			topptips.log()
			tt=topptips.closing
		powerplay=Tips("powerplay", "mq")
		powerplay.get_html()
		powerplay.get_data()
		powerplay.erase_html()
		if powerplay.closing!=pp:
			print time.ctime()+"powerplayupdate"
			powerplay.log()
			pp=powerplay.closing
		print time.ctime()+" waiting..."
		time.sleep(180)
main()	

