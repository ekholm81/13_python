
o=open("rader.txt","w")
def same_sign(b, a):
	if (b==0):
		return (a==0 or a==3 or a==4 or a==6)
	if (b==1):
		return (a==1 or a==3 or a==5 or a==6)
	if (b==2):
		return (a==2 or a==4 or a==5 or a==6)
def s_in_m(s,m):
	for i in range(8):
		if not same_sign(s[i],m[i]):
			return False
	return True

def printr(r):
	
	o.write("E,")
	for i in range(len(r)):
		if r[i]==0:
			o.write("1")
		elif r[i]==1:
			o.write("X")
		elif r[i]==2:
			o.write("2")
		if i+1==len(r):
			o.write("\n")
		else:
			o.write(",")

f=open("msys.txt","r")
m=[]
for i in f:
	tmp=[]
	c=0
	for j in i:
		tmp.append(ord(j)-48)
		c=c+1
		if c==8:
			break
	m.append(tmp)
print m
for a in range(3):
	for b in range(3):
		for c in range(3):
			for d in range(3):
				for e in range(3):
					for f in range(3):
						for g in range(3):
							for h in range(3):
								for x in range (len(m)):
									if 	s_in_m([a,b,c,d,e,f,g,h],m[x]):
										printr([a,b,c,d,e,f,g,h])
									
