import random
import re
import matplotlib.pyplot as plt
class Jaromir:
	def __init__(self,tecken,odds,oms):
		self.odds=odds
		self.chance=self.get_chance(odds)
		self.streck=tecken
		self.oms=oms
		self.exp=[]
		self.s=[]

	def get_chance(self,odds):
		chance=[[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0]]			
		for i in range(8):
			for j in range (3):
				chance[i][j]=(1.00/odds[i][j])*1.00/(1.00/odds[i][0]+1.00/odds[i][1]+1.00/odds[i][2])
		return chance
	
	def get_modchance(self,inc):
		chance=[[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0]]			
		odds=[[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0]]	
		for i in range(8):
			for j in range (3):
				odds[i][j]=self.odds[i][j]-inc		
		for i in range(8):
			for j in range (3):
				chance[i][j]=(1.00/odds[i][j])*1.00/(1.00/odds[i][0]+1.00/odds[i][1]+1.00/odds[i][2])
		return chance	
	
	def get_chart(self):
		self.oms= int(re.sub("[^0-9]", "", self.oms))
		utd=self.oms*0.70
		chance=self.get_modchance(0)
		numrows=[]
		ch=[]
		aa=0
		chosen=[]
		for a in range(3):
			for b in range(3):
				for c in range(3):
					for d in range(3):
						for e in range(3):
							for f in range(3):
								for g in range(3):
									for h in range(3):
										if a==1 and b==1 and c==1 and d==1 and e==1 and f==1 and g==1 and h==1:															
											print ((self.streck[0][a])*0.01*(self.streck[1][b])*0.01*(self.streck[2][c])*0.01*(self.streck[3][d])*0.01*(self.streck[4][e])*0.01*(self.streck[5][f])*0.01*(self.streck[6][g])*0.01*(self.streck[7][h])*0.01)*utd
										numrows.append((self.streck[0][a])*0.01*(self.streck[1][b])*0.01*(self.streck[2][c])*0.01*(self.streck[3][d])*0.01*(self.streck[4][e])*0.01*(self.streck[5][f])*0.01*(self.streck[6][g])*0.01*(self.streck[7][h])*0.01)
										ch.append(self.chance[0][a]*self.chance[1][b]*self.chance[2][c]*self.chance[3][d]*self.chance[4][e]*self.chance[5][f]*self.chance[6][g]*self.chance[7][h])
										if numrows[-1]/(ch[-1])<0.7:
											aa=aa+1	
											chosen.append([numrows[-1],ch[-1]])
		print len(chosen)
		sann=[]
		for i in range(1000):
			sann.append(0)
			for j in range(len(chosen)):
				#print utd/(chosen[j][0]*self.oms)
				if utd/(chosen[j][0]*self.oms)>(i+1)*100:
					sann[-1]+=chosen[j][1]
		plt.plot(sann)
		plt.show()
		
	def get_medel(self):	
		self.oms= int(re.sub("[^0-9]", "", self.oms))
		utd=self.oms*0.70
		for x in range(10):	
			chance=self.get_modchance(x/10.00)
			numrows=[]
			ch=[]
			mch=[]
			dic={}
			for a in range(3):
				for b in range(3):
					for c in range(3):
						for d in range(3):
							for e in range(3):
								for f in range(3):
									for g in range(3):
										for h in range(3):															
											numrows.append(self.oms*(self.streck[0][a])*0.01*(self.streck[1][b])*0.01*(self.streck[2][c])*0.01*(self.streck[3][d])*0.01*(self.streck[4][e])*0.01*(self.streck[5][f])*0.01*(self.streck[6][g])*0.01*(self.streck[7][h])*0.01)
											ch.append(self.chance[0][a]*self.chance[1][b]*self.chance[2][c]*self.chance[3][d]*self.chance[4][e]*self.chance[5][f]*self.chance[6][g]*self.chance[7][h])
											mch.append(chance[0][a]*chance[1][b]*chance[2][c]*chance[3][d]*chance[4][e]*chance[5][f]*chance[6][g]*chance[7][h])
											dic.update({(utd/numrows[-1])*mch[-1]:[utd/numrows[-1]*ch[-1],ch[-1]]})	
			counter=0
			exp=0
			s=0
			for key in sorted(dic,reverse=True):
				if counter==500:
					break
				counter=counter+1
				exp+=dic[key][0]
				s+=dic[key][1]
			self.exp.append(round(exp,2))
			self.s.append(round(s*100.00,2))
		#print self.exp
		#print self.s
		
		return 1	

		
