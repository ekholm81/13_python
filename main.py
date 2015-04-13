# coding: utf-8
import os
import time
user="mq"

global_crossed=[]
global_odds=[]
global_gamestring=""
global_closing=""
global_oms=""

def get_html():
	os.system("wget https://www.svenskaspel.se/topptipset/spela -O /home/"+user+"/13_python/wget/topptipset  -q --no-check-certificate")
	
def erase_html():
	os.system("rm /home/"+user+"/13_python/wget/topptipset")
	
def get_data():
	gamestring=[]
	odds=[]
	crossed=[]
	oms=""
	closing=""
	
	f=open("/home/"+user+"/13_python/wget/topptipset", 'r')
	for line in f.readlines():
		if line[:23]=="svs.tipset.data.draws =":
			break
	f.close()
	counter=0
	for i in range(len(line)):
		if line[i:i+19]=="eventDescription\":\"":
			tmp=""
			for j in range(100):
				if line[i+j+19]!='\"':
					tmp+=line[i+j+19]
				else:						
					gamestring.append(tmp)
					break
			counter+=1
			if counter==8:
				break
	counter=0
	for i in range(len(line)):
		if line[i:i+9]=="\"odds\":{\"":
			tmp=""
			idx=9+i
			b=True
			for j in range(50):
				if ord(line[idx+j])>47 and ord(line[idx+j])<58:
					tmp+=line[idx+j]
					continue
				if line[idx+j]==',' and b==True:
					tmp+='.'
					continue
				if b==True:
					tmp+=' '
				if line[idx+j]=='"':
					b=not b
			odds.append(tmp)
			counter+=1
			if counter==8:
				break
	counter=0
	for i in range(len(line)):
		if line[i:i+16]=="svenskaFolket\":{":
			tmp=""
			idx=16+i
			for j in range(56):
				if ord(line[idx+j])>47 and ord(line[idx+j])<58:
					tmp+=line[idx+j]
					continue
				if line[idx+j]=='%':
					tmp+=' '
					continue
			crossed.append(tmp)
			counter+=1
			if counter==8:
				break
	for i in range(len(line)):
		if line[i:i+18]=="\"currentNetSale\":\"":
			idx=i+18
			tmp=""
			for j in range(25):
				if line[idx+j]=='"':
					break
				tmp+=line[idx+j]
			oms=tmp
			break
	for i in range(len(line)):
		if line[i:i+5]=="nger ":
			idx=i+5
			tmp=""
			for j in range(25):
				if line[idx+j]=='"':
					break
				tmp+=line[idx+j]
			closing=tmp
			break
	num_odds=[]
	num_crossed=[]
	tmp_arr=[]		
	for i in range(8):
		odds[i]=odds[i][4:]
		odds[i]= odds[i].replace("   ", ",",2)
		crossed[i]= crossed[i].replace(" ", ",",2)
		odds[i]= odds[i].replace(" ", "")
	for i in range(8):
		tmp_arr = [float(k) for k in odds[i].split(',')]
		num_odds.append(tmp_arr)
		tmp_arr = [int(k) for k in crossed[i].split(',')]
		num_crossed.append(tmp_arr)
	global global_crossed
	global_crossed=num_crossed
	global global_closing
	global_closing=closing
	global global_gamestring
	global_gamestring=gamestring
	global global_odds
	global_odds=num_odds
	global global_oms
	global_oms=oms

def log(gamestring,odds,crossed,closing,oms):
	print time.ctime()+" Logging..."
	f= open(os.path.join("/home/"+user+"/13_python/data", closing+time.ctime()), 'w')
	for i in range (8):
		f.write(str(crossed[i][0])+" "+str(crossed[i][1])+" "+str(crossed[i][2])+"\n")
	for i in range (8):	
		f.write(str(odds[i][0])+" "+str(odds[i][1])+" "+str(odds[i][2])+"\n")
	for i in range (8):
		f.write(gamestring[i]+"\n")
	f.write(closing +"\n"+oms+"\n")
	f.close()

#def alert:
		
		
def main():
	get_html()
	get_data()
	while True:	
		curr_gamestring=global_gamestring
		curr_odds=global_odds
		curr_crossed=global_crossed
		curr_closing=global_closing
		curr_oms=global_oms
		time.sleep(900)
		get_html()
		get_data()
		if curr_closing !=global_closing:
			log(curr_gamestring,curr_odds,curr_crossed,curr_closing,curr_oms)
		#alert()

main()	

