import random
streck=[[43, 29, 28],
		[18, 28, 54],
		[49, 29, 22],
		[62, 22, 16],
		[69, 18, 13],
		[36, 29, 35],
		[24, 26, 50],
		[50, 29, 21]]

		
odds=[[1.98, 3.25, 3.8],
	[2.23, 3.2, 3.2],
	[1.62, 3.7, 5.3],
	[1.73, 3.75, 4.3],
	[1.78, 3.1, 4.35],
	[2.15, 3.35, 2.87],
	[1.33, 4.35, 7.5],
	[2.93, 3.1, 2.23]]

chance=[[1.98, 3.25, 3.8],
	[2.23, 3.2, 3.2],
	[1.62, 3.7, 5.3],
	[1.73, 3.75, 4.3],
	[1.78, 3.1, 4.35],
	[2.15, 3.35, 2.87],
	[1.33, 4.35, 7.5],
	[2.93, 3.1, 2.23]]

oms =1407074
utd=oms*0.700	

tecken=[[52, 28, 20],
		[43, 30, 27],
		[67, 21, 12],
		[55, 26, 19],	
		[59, 25, 16],
		[35, 29, 36],
		[69, 19, 12],
		[26, 28, 46]]	
for i in range(8):
	for j in range (3):
		chance[i][j]=1.00/odds[i][j]*1.00/(1.00/odds[i][0]+1.00/odds[i][1]+1.00/odds[i][2])
		tecken[i][j]=int(round(streck[i][j]*0.01*oms,0))

fi=open("rader",'w')

def getsign(a):
	if a==0:
		return '1'
	if a==1:
		return 'X'
	if a==2:
		return '2'
oms =1592494.00
utd=oms*0.7000000
for a in range(3):
	for b in range(3):
		for c in range(3):
			for d in range(3):
				for e in range(3):
					for f in range(3):
						for g in range(3):
							for h in range(3):
								cc=oms
								cc=cc*(streck[0][a])*0.01*(streck[1][b])*0.01*(streck[2][c])*0.01*(streck[3][d])*0.01*(streck[4][e])*0.01*(streck[5][f])*0.01*(streck[6][g])*0.01*(streck[7][h])*0.01
								if a==0 and b==0 and c ==0 and d==1 and e==0 and f==1 and g==1 and h==2:
									fi.write( repr(utd/cc)+"\n")
							
