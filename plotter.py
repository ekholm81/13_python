# coding: utf-8
import os
import time
from Tips import Tips
from Jaromir import Jaromir
from Mailer import Mailer
import matplotlib.pyplot as plt
def main():

	topptips=Tips("topptipset", "mq")
	topptips.get_data()
		
	j=Jaromir(topptips.ncrossed, topptips.nodds, topptips.oms)
	j.get_chart()
main()	

