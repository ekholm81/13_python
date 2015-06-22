# coding: utf-8

import os
import time
class Tips:
	def __init__(self, tips, user):		
		self.allodds=True
		self.ncrossed=[]
		self.crossed=[]
		self.nodds=[]
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
	
	def get_closing(self):
		self.get_html()
		line=""
		f=open(self.path, 'r')
		for line in f.readlines():
			if line[:23]=="svs.tipset.data.draws =":
				break
		f.close()
		if line[:23]!="svs.tipset.data.draws =":
			self.internetERROR=True
			return
		self.internetERROR=False
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
		self.erase_html()
		return self.closing
	def get_data(self):	
		self.get_html()
		self.crossed=[]
		self.odds=[]
		self.gamestring=[]
		self.oms=""
		line=""
		f=open(self.path, 'r')
		for line in f.readlines():
			if line[:23]=="svs.tipset.data.draws =":
				break
		f.close()
		if line[:23]!="svs.tipset.data.draws =":
			self.internetERROR=True
			return
		self.internetERROR=False
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
		self.zeroodds=False
		for i in range(len(line)):
			if line[i:i+7]=="\"odds\":":
				if line[i+7]=='n':
					counter+=1
					self.odds.append("    0    0      0             ")
					self.zeroodds=True
					if counter==8:
						break
					continue
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
				for j in range(40):
					if ord(line[idx+j])>47 and ord(line[idx+j])<58:
						tmp+=line[idx+j]
						continue
					if line[idx+j]==',':
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
		self.nodds=num_odds
		self.ncrossed=num_crossed
		self.erase_html()

	def log(self,c):	
		try:		
			f= open(os.path.join("/home/"+self.user+"/13_python/data", self.tips+"-"+c+"-"+time.ctime()), 'w')
		except:
			return
		if self.internetERROR:
			f.write("internetERROR\n")
			return
		for i in range (8):
			f.write(str(self.ncrossed[i][0])+" "+str(self.ncrossed[i][1])+" "+str(self.ncrossed[i][2])+"\n")
		for i in range (8):	
			f.write(str(self.nodds[i][0])+" "+str(self.nodds[i][1])+" "+str(self.nodds[i][2])+"\n")
		for i in range (8):
			f.write(self.gamestring[i]+"\n")
		f.write(c +"\n"+self.oms+"\n")
		f.close()
