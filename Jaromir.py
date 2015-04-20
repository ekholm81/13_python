import random
import re
class Jaromir:
	def __init__(self,tecken,odds,oms):
		self.chance=self.get_chance(odds)
		self.medel=self.get_medel(self.chance,tecken,oms)

	def get_chance(self,odds):
		chance=[[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0]]			
		for i in range(8):
			for j in range (3):
				chance[i][j]=(1.00/odds[i][j])*1.00/(1.00/odds[i][0]+1.00/odds[i][1]+1.00/odds[i][2])
		return chance
	
	def get_medel(self,chance, streck, oms):
		oms= int(re.sub("[^0-9]", "", oms))
		utd=oms*0.70
		s=0
		numrows=[]
		ch=[]
		t=0
		for a in range(3):
			for b in range(3):
				for c in range(3):
					for d in range(3):
						for e in range(3):
							for f in range(3):
								for g in range(3):
									for h in range(3):								
										numrows.append(oms*(streck[0][a])*0.01*(streck[1][b])*0.01*(streck[2][c])*0.01*(streck[3][d])*0.01*(streck[4][e])*0.01*(streck[5][f])*0.01*(streck[6][g])*0.01*(streck[7][h])*0.01)
										ch.append(chance[0][a]*chance[1][b]*chance[2][c]*chance[3][d]*chance[4][e]*chance[5][f]*chance[6][g]*chance[7][h])
										t+=ch[-1]
										s+=(utd/numrows[-1])*ch[-1]
		self.sane=t
		return s
