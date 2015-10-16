#include <iostream>
#include <algorithm>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <sys/wait.h>
#include <signal.h>
#include <sstream>
#include <string>
#define DPORT "10000"
#define BADURL "HTTP/1.1 301 Moved Permanently\r\nLocation: http://www.ida.liu.se/~TDTS04/labs/2011/ass2/error1.html\r\n\r\n"
#define BADTEXT "HTTP/1.1 301 Moved Permanently\r\nLocation: http://www.ida.liu.se/~TDTS04/labs/2011/ass2/error2.html\r\n\r\n"
#define BACKLOG 10     // #pending connections queue will hold
using namespace std;
string bad_strings[9] = { "norrköping", "spongebob", "norrkoping","paris hilton","britney spears","parishilton","britneyspears","britney+spears","paris+hilton"};
void sigchld_handler(int s)
{
    int saved_errno = errno;
    while(waitpid(-1, NULL, WNOHANG) > 0);
    errno = saved_errno;
}

char lc(char in)
{
    if(in<='Z' && in>='A')
        return in-('Z'-'z');
    return in;
}

bool forbidden_strings(string request)
{
    string s=request;
    transform(s.begin(),s.end(),s.begin(),lc);
    for (int i = 0; i < 9; i++)
    {
        if((s).find(bad_strings[i])!=string::npos) return true;
    }
    return false;
}

//Make requiered changes to the header
string create_header(string request)
{
    istringstream ss(request);
    string s;
    string res="";
    int i=0;
    while(getline(ss,s))
    {
        if(i==0)
        {
            i++;
            res+=(s+"\n");
        }
        else
        {
            transform(s.begin(),s.end(),s.begin(),lc);
            if(s.substr(0,11)=="connection:")
            {
                res+="connection: close\r\n";
            }
            else
            {
                res+=(s+"\n");
            }
        }
    }
    res+="\r\n";
    return res;
}

//Filters out the Host from headerlines
string getHost(string request)
{
    string s;
    string res="";
    istringstream ss((request));
    while(getline((ss),s))
    {
        if(s.substr(0,6)=="Host: "||s.substr(0,6)=="host: ")
        {
            for(int i=6; i<543; i++)
            {
                if(s[i]=='\r')return res;
                res+=s[i];
            }
        }
    }
    cout<<"ERROR IN getHost\n\n";
    return "";
}

//Detects if httpdata is textformat
bool isText(string resp)
{
    string s;
    //cout<<resp<<"\n";
    istringstream ss((resp));
    while(getline((ss),s))
    {
        if(s.substr(0,14)=="Content-Type: "||s.substr(0,14)=="content-type: ")
        {
            if(s.substr(14,4)=="text")return true;

        }
    }
    return false;
}

//Forwards request to the real webserver and recieves the response.
//When the respons is fetched this function also scans the content to detect the "bad words"
void serverGetSend(string host,string request, int client_socket)
{
    int retval;
    int sock;
    struct addrinfo hints, *sinfo, *it;

    memset(&hints, 0, sizeof hints); // make sure the struct is empty
    hints.ai_family = AF_UNSPEC;     // don't care IPv4 or IPv6
    hints.ai_socktype = SOCK_STREAM; // TCP stream sockets
    hints.ai_flags = AI_PASSIVE;     // fill in my IP for me
    cout<<host.c_str()<<"\n";
    if((retval=getaddrinfo(host.c_str(),"80",&hints,&sinfo))!=0)
    {
        fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(retval));
        return;
    }
    for(it=sinfo; it!=NULL; it=it->ai_next)
    {
        if((sock=socket(it->ai_family,it->ai_socktype,it->ai_protocol))==-1)
        {
            perror("server: socket");
            continue;
        }
        if(connect(sock,it->ai_addr,it->ai_addrlen)==-1)
        {
            close(sock);
            perror("client: connect");
            continue;
        }
        break;
    }

    if(it==NULL)
    {
        fprintf(stderr, "server: failed to bind\n");
        exit(1);
    }
    if(send(sock, request.c_str(), strlen(request.c_str()), 0) == -1)cout<<"failed to send\n\n";

    char buf[65530];
    int numbytes;
    string rec;
    bool text=false;
    while(true)
    {
        numbytes = recv(sock, buf, sizeof(buf), 0);
        if(numbytes==0)break;
        rec=string(buf,numbytes);
        if(isText(rec) && forbidden_strings(rec))
        {
            send(client_socket,BADTEXT,strlen(BADTEXT),0);
        }
        else
        {
            int sent=0;
            while (sent < rec.size())
            {
                numbytes = send(client_socket, rec.c_str() + sent, rec.size()-sent, 0);
                sent = sent + numbytes;
            }
        }
    }
    freeaddrinfo(sinfo);
    close(sock);
}

//This function listens for new connections and catches the initial GET request from the client and scans the URL for inappropriate words.
void listener(char* port)
{
    struct sigaction sa;
    int yes=1;
    int retval;
    int lis_socket, data_socket;
    struct addrinfo hints, *sinfo, *it;

    memset(&hints, 0, sizeof hints); // make sure the struct is empty
    hints.ai_family = AF_UNSPEC;     // don't care IPv4 or IPv6
    hints.ai_socktype = SOCK_STREAM; // TCP stream sockets
    hints.ai_flags = AI_PASSIVE;     // fill in my IP for me

    if((retval=getaddrinfo(NULL,port,&hints,&sinfo))!=0)
    {
        fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(retval));
        return;
    }

    for(it=sinfo; it!=NULL; it=it->ai_next)
    {
        if((lis_socket=socket(it->ai_family,it->ai_socktype,it->ai_protocol))==-1)
        {
            perror("server: socket");
            continue;
        }
        //set socket options
        //avoid error msg that socket is occupied
        if (setsockopt(lis_socket, SOL_SOCKET, SO_REUSEADDR, &yes,sizeof(int)) == -1)
        {
            perror("setsockopt");
            exit(1);
        }
        //bind port and socket
        if(bind(lis_socket,it->ai_addr,it->ai_addrlen)==-1)
        {
            close(lis_socket);
            perror("serverGet: bind");
            continue;
        }
        break;
    }
    //sinfo only needed to generate socket
    freeaddrinfo(sinfo);
    if(it==NULL)
    {
        fprintf(stderr, "server: failed to bind\n");
        exit(1);
    }
    //listen on socket for incoming connections
    if (listen(lis_socket, BACKLOG) == -1)
    {
        perror("listen");
        exit(1);
    }
    sa.sa_handler = sigchld_handler; // reap all dead processes
    sigemptyset(&sa.sa_mask);
    sa.sa_flags = SA_RESTART;
    if (sigaction(SIGCHLD, &sa, NULL) == -1)
    {
        perror("sigaction");
        exit(1);
    }
    struct sockaddr_storage client_addr;
    socklen_t addrlen;
    addrlen=sizeof client_addr;
    while(true)
    {
        if(data_socket=accept(lis_socket,(struct sockaddr *)&client_addr,&addrlen)==-1)
        {
            perror("accept");
            continue;
        }
        if (!fork())   // this is the child process
        {
            close(lis_socket); // child doesn't need the listener
            char buf[2048];
            int numbytes=recv(data_socket, buf, 2048, 0);
            string request = string(buf, numbytes);
            buf[numbytes] = '\0';
            string new_header=create_header(request);
            string host=getHost(request);
            if(forbidden_strings(host))
            {
                cout<<"FORBIDDEN URL DETECTED\N";
                send(data_socket,BADURL,strlen(BADURL),0);
            }
            else
            {
                serverGetSend(host,new_header, data_socket);
            }
            close(data_socket);
            exit(0);//kill child
        }
        close(data_socket);  // parent doesn't need this
    }
}





int main(int argc, char *argv[])
{
    listener(argv[1]);
    return 1;
}
