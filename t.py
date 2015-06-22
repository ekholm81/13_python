import smtplib

FROM = 'user@gmail.com'
TO = ['recepient@mailprovider.com'] #must be a list
SUBJECT = "Testing sending using gmail"
TEXT = "Testing sending mail using gmail servers"

# Prepare actual message
msg = "hej\nhej\nhej"

server = smtplib.SMTP('smtp.gmail.com',587)
server.ehlo()
server.starttls()
server.login("charlieaustin853@gmail.com", "paramaribo")
server.sendmail("charlieaustin853@gmail.com", "charlieaustin853@gmail.com", msg)
server.close()
