

import os
import time
class Tips:
	def __init__(self, tips, user):
		self.crossed=[]
		self.odds=[]
		self.gamestring=[]
		self.closing=""
		self.oms=""
		self.tips=tips
		self.user=user
		self.path="/home/"+self.user+"/13_python/wget/"+self.tips
	def get_html(self):
		os.system("wget https://www.svenskaspel.se/"+self.tips+"/spela -O /home/"+self.user+"/13_python/wget/"+self.tips+"  -q --no-check-certificate")

	def erase_html(self):
		os.system("rm /home/"+self.user+"/13_python/wget/"+self.tips)
	
	def get_data(self):	
		line=""
		f=open(self.path, 'r')
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
						self.gamestring.append(tmp)
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
				self.odds.append(tmp)
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
				self.crossed.append(tmp)
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
				self.oms=tmp
				break
		for i in range(len(line)):
			if line[i:i+5]=="nger ":
				idx=i+5
				tmp=""
				for j in range(25):
					if line[idx+j]=='"':
						break
					tmp+=line[idx+j]
				self.closing=tmp
				break
		num_odds=[]
		num_crossed=[]
		tmp_arr=[]		
		for i in range(8):
			self.odds[i]=self.odds[i][4:]
			self.odds[i]= self.odds[i].replace("   ", ",",2)
			self.crossed[i]= self.crossed[i].replace(" ", ",",2)
			self.odds[i]= self.odds[i].replace(" ", "")
		for i in range(8):
			tmp_arr = [float(k) for k in self.odds[i].split(',')]
			num_odds.append(tmp_arr)
			tmp_arr = [int(k) for k in self.crossed[i].split(',')]
			num_crossed.append(tmp_arr)
		self.odds=num_odds
		self.crossed=num_crossed
		

	def log(self):
		print time.ctime()+" Logging..."
		f= open(os.path.join("/home/"+self.user+"/13_python/data", self.closing+time.ctime()), 'w')
		for i in range (8):
			f.write(str(self.crossed[i][0])+" "+str(self.crossed[i][1])+" "+str(self.crossed[i][2])+"\n")
		for i in range (8):	
			f.write(str(self.odds[i][0])+" "+str(self.odds[i][1])+" "+str(self.odds[i][2])+"\n")
		for i in range (8):
			f.write(self.gamestring[i]+"\n")
		f.write(self.closing +"\n"+self.oms+"\n")
		f.close()
