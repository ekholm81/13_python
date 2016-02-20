

def printa(s):
	a=""
	for i in range(8):
		if s[i]=="0":
			a+="1"
		if s[i]=="1":
			a+="X"
		if s[i]=="2":
			a+="2"
	print a


f=open("rader.txt","r")
rader=[]
corr="2X1121X1"
imp= [0,0,0,0,0,1,0,0]
r=[0,0,0,0,0,0,0,0,0]
for line in f:
	l=line[:-1]
	nr=0
	for j in range(8):	
		if l[j]==corr[j]:
			nr=nr+1
	if j==7:
		if nr==7:
			printa(line[:-1])
		r[nr]=r[nr]+1
for i in range (len(r)):
	print repr(r[i])+ " rader med " +repr(i)+ " ratt"


