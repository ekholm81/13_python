# coding: utf-8

import os
import time
import smtplib
class Mailer:
	def __init__(self):		
		pass
	def ready(self):
		return True
		
	def send_mail(self,spelstopp,exp,s):
		FROM = 'charlieaustin853@gmail.com'
		TO = ['charlieaustin853@gmail.com'] #must be a list
		SUBJECT = "Hett tt"
		TEXT = spelstopp
		for i in range(len(exp)):
			TEXT+="\n"+repr(i)+"   EXP(500): "+repr(exp[i])+"   Chance: "+repr(s[i])+"%"
		msg = """\From: %s\nTo: %s\nSubject: %s\n\n%s
		""" % (FROM, ", ".join(TO), SUBJECT, TEXT)
		server = smtplib.SMTP('smtp.gmail.com',587)
		server.ehlo()
		server.starttls()
		server.login("charlieaustin853@gmail.com", "paramaribo")
		server.sendmail("charlieaustin853@gmail.com", "charlieaustin853@gmail.com", msg)
		server.close()
