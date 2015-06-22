ref="rows.txt"
src="o.txt"
srows=[]
mrows=[]

def get_msz(m):
	n=1
	for i in range(8):
		if m[i]==6:
			n=n*3
		elif m[i]>=3:
			n=n*2
	return n
def same_sign(b, a):
	if (b==0):
		return (a==0 or a==3 or a==4 or a==6)
	if (b==1):
		return (a==1 or a==3 or a==5 or a==6)
	if (b==2):
		return (a==2 or a==4 or a==5 or a==6)

def same_m_sign( b, a):
    if(b==6):
        return True;
    if(b==5):
        return (a==1 or a==2 or a==5)
    if(b==4):
		return (a==0 or a==2 or a==4)
    if(b==3):
		return (a==0 or a==1 or a==3)
    return (a==b)


def s_in_m(s,m):
	for i in range(8):
		if not same_sign(s[i],m[i]):
			return False
	return True

def m_in_m(m1,m2):
	for i in range(8):
		if not same_m_sign(m1[i],m2[i]):
			return False
	return True

f=open(src)
for line in f:
	tmp=[]
	for i in line:
		if i!='\n':
			tmp.append(ord(i)-48)
	srows.append(tmp)	

fi=open(ref)
for line in fi:
	tmp=[]
	for i in line:
		if i!='\n':
			tmp.append(ord(i)-48)
	mrows.append(tmp)	
n=0
notf=[]
mult=[]
multm=[]
m=0

for i in range (len(srows)):
	found=False
	for j in range (len(mrows)):
		if s_in_m(srows[i],mrows[j]):
			if found==True:
				mult.append(srows[i])
			else:
				found=True
	if found==False:
		notf.append(srows[i])
		
for i in range (len(mrows)):
	m=m+get_msz(mrows[i])
	for j in range (len(mrows)):
		if i!=j and m_in_m(mrows[i],mrows[j]):
			multm.append(mrows[i])

			
print "notf " +repr(len(notf))
print "mults "+repr(len(mult))
print "multm "+repr(len(multm))
print "numsrows "+repr(len(srows))
print "mrows "+repr(m)
