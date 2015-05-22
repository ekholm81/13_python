import sys
import math
import time
from random import randint
import random

def same_sign(a,b):
	if b==0:
		return (a==0 or a==3 or a==4 or a==6)
	if b==1:
		return (a==1 or a==3 or a==5 or a==6)
	if b==2:
		return (a==2 or a==4 or a==5 or a==6)

def in_row(msys,row):
	if not same_sign(msys[0],row[0]):
		return False
	if not same_sign(msys[1],row[1]):
		return False
	if not same_sign(msys[2],row[2]):
		return False
	if not same_sign(msys[3],row[3]):
		return False
	if not same_sign(msys[3],row[3]):
		return False
	if not same_sign(msys[4],row[4]):
		return False
	if not same_sign(msys[5],row[5]):
		return False
	if not same_sign(msys[6],row[6]):
		return False
	if not same_sign(msys[7],row[7]):
		return False
	return True
		
def gen_msys():
	#fil = open('workfile', 'w')
	msys=[]
	for a in range(7):
		for b in range(7):	
			for c in range(7):
				for d in range(7):
					for e in range(7):
						for f in range(7):
							for g in range(7):
								for h in range(7):
									#fil.write(repr([a,b,c,d,e,f,g,h])+"\n")	 
									msys.append([a,b,c,d,e,f,g,h])
	#sys.exit(1)
	return msys

def form(msys,idx):
	for i in range(8):
		if msys[idx][i]>2:
			continue
		elif msys[idx][i]==0:
			msys[idx+pow(7,7-i)*3]=0						
			msys[idx+pow(7,7-i)*4]=0						
			msys[idx+pow(7,7-i)*6]=0	
			continue	
		elif msys[idx][i]==1:
			msys[idx+pow(7,7-i)*3]=0						
			msys[idx+pow(7,7-i)*5]=0						
			msys[idx+pow(7,7-i)*6]=0
			continue
		elif msys[idx][i]==2:
			msys[idx+pow(7,7-i)*4]=0						
			msys[idx+pow(7,7-i)*5]=0						
			msys[idx+pow(7,7-i)*6]=0
			continue	
	return msys			
									
def main(rrows):
	print time.ctime()
	msys=gen_msys()
	random.shuffle(rrows)
	print len(rrows)
	disc=[]
	for i in range(len(rrows)):
		print repr(i)+" : "+repr(len(msys))
		d=0
		it=len(msys)
		for j in range(it):
			if in_row(msys[j-d],rrows[i]):				
				msys[j-d],msys[-1]=msys[-1],msys[j-d]
				msys.pop()
				d=d+1
	print time.ctime()


t=[]	
for a in range(3):
	for b in range(3):
		for c in range(3):
			for d in range(3):
				for e in range(3):
					for f in range(3):
						for g in range(1):
							for h in range(3):	
								t.append([a,b,c,d,e,f,g,h])
main(t)
