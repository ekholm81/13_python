/*
** server.c -- a stream socket server demo
*/
#include <iostream>

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
#include <string.h>
#define PORT "10000"  // the port users will be connecting to
#define URL "HTTP/1.1 301 Found\r\nLocation: http://www.ida.liu.se/~TDTS04/labs/2011/ass2/error1.html\r\n\r\n"
#define BACKLOG 10     // how many pending connections queue will hold
using namespace std;
string bad_strings[7] = {"britney spears", "spongebob", "norrköping", "norrkoping","paris hilton","britney+spears","paris+hilton"};
void sigchld_handler(int s)
{
    // waitpid() might overwrite errno, so we save and restore it:
    int saved_errno = errno;
    while(waitpid(-1, NULL, WNOHANG) > 0);
    errno = saved_errno;
}

void edit_cHeader(string *request){
    size_t f=(*request).find("Connection: keep-alive");
    if(f!=string::npos){
        (*request).erase(f,22);
        (*request).insert(f,"Connection: Close");
    }
}

bool forbidden_url(string *request){
     for (int i = 0; i < 7; i++){
        if((*request).find(bad_strings[i])!=string::npos) return true;
     }
     return false;
}
string const& getHost(string *request){
    string s;
    string res="";
    istringstream ss((*request));
    while(getline((ss),s)){
        if(s.substr(0,6)=="Host: "){
            s.erase(0,6);
            s.erase(s.length()-1,1);
            res.append(s);
        }
    }
    return res;
}




void clientSend(string *request, int client_socket){
    edit_cHeader(request);
    string const&host=getHost(request);
    const char *host2="www.dn.se";
    cout<<"len:"<<(host.length())<<":"<<host<<"\n";
    cout<<"len:"<<strlen(host2)<<":"<<host2<<"\n";
    int retval;
    int sock;
    struct addrinfo hints, *sinfo, *it;

    memset(&hints, 0, sizeof hints); // make sure the struct is empty
    hints.ai_family = AF_UNSPEC;     // don't care IPv4 or IPv6
    hints.ai_socktype = SOCK_STREAM; // TCP stream sockets
    hints.ai_flags = AI_PASSIVE;     // fill in my IP for me

    if((retval=getaddrinfo(host.c_str(),"80",&hints,&sinfo))!=0){
        fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(retval));
        return;
    }

    for(it=sinfo;it!=NULL;it=it->ai_next){
        if((sock=socket(it->ai_family,it->ai_socktype,it->ai_protocol))==-1){
            perror("server: socket");
            continue;
        }
        if(connect(sock,it->ai_addr,it->ai_addrlen)==-1){
            close(sock);
            perror("client: connect");
            continue;
        }
        break;
    }

    if(it==NULL){
        fprintf(stderr, "server: failed to bind\n");
        exit(1);
    }
    cout<<(*request);
    if(send(sock,(*request).c_str(),strlen((*request).c_str()),0)==-1)cout<<"error while sending"<<"\n";
    char rec_buf[512];
    int numbytes = 1;
    string recv_data;
    numbytes = recv(sock, rec_buf, sizeof(rec_buf), 0);
    recv_data = string(rec_buf,numbytes);
    cout<<"res data:    "<<recv_data;
}

 map<string, string> ref_header(string request){
     map<string, string> header;
     istringstream ss(request,s);
     int i=0;
     while(getline(ss)){
        if(i==0){
            i++;
            header->insert(pair<string,string>("req",s));
        }
        else{
            string tmp:
            istringstream lss(s);
            lss>>tmp;
            transform(tmp.begin(),tmp.end(),tmp.begin(),::tolower());
        }
     }
 }

void serverGet(){

    struct sigaction sa;
    int yes=1;
    int retval;
    int lis_socket, data_socket;
    struct addrinfo hints, *sinfo, *it;

    memset(&hints, 0, sizeof hints); // make sure the struct is empty
    hints.ai_family = AF_UNSPEC;     // don't care IPv4 or IPv6
    hints.ai_socktype = SOCK_STREAM; // TCP stream sockets
    hints.ai_flags = AI_PASSIVE;     // fill in my IP for me

    if((retval=getaddrinfo(NULL,PORT,&hints,&sinfo))!=0){
        fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(retval));
        return;
    }

    for(it=sinfo;it!=NULL;it=it->ai_next){
        if((lis_socket=socket(it->ai_family,it->ai_socktype,it->ai_protocol))==-1){
            perror("server: socket");
            continue;
        }
        //set socket options
        //avoid error msg that socket is occupied
        if (setsockopt(lis_socket, SOL_SOCKET, SO_REUSEADDR, &yes,sizeof(int)) == -1) {
            perror("setsockopt");
            exit(1);
        }
        //bind port and socket together
        if(bind(lis_socket,it->ai_addr,it->ai_addrlen)==-1){
            close(lis_socket);
            perror("serverGet: bind");
            continue;
        }
        break;
    }
    //sinfo only needed to generate socket
    freeaddrinfo(sinfo);
    if(it==NULL){
        fprintf(stderr, "server: failed to bind\n");
        exit(1);
    }
    //listen on socket for incoming connections
    if (listen(lis_socket, BACKLOG) == -1) {
        perror("listen");
        exit(1);
    }
    sa.sa_handler = sigchld_handler; // reap all dead processes
    sigemptyset(&sa.sa_mask);
    sa.sa_flags = SA_RESTART;
    if (sigaction(SIGCHLD, &sa, NULL) == -1) {
        perror("sigaction");
        exit(1);
    }
    struct sockaddr_storage client_addr;
    socklen_t addrlen;
    addrlen=sizeof client_addr;
    while(true){
        if(data_socket=accept(lis_socket,(struct sockaddr *)&client_addr,&addrlen)==-1){
            perror("accept");
            continue;
        }
         if (!fork()) { // this is the child process
            close(lis_socket); // child doesn't need the listener
            char buf[2048];
            int numbytes=recv(data_socket, buf, 2048, 0);
            string request = string(buf, numbytes);
            map<string, string> header= ref_header(request);
            cout<<request;
            if(forbidden_url(&request)){
                send(data_socket,URL,strlen(URL),0);
            }
            else{
                clientSend(&request,  data_socket);
            }
            close(data_socket);
            exit(0);//kill child
        }
        close(data_socket);  // parent doesn't need this
    }
}





int main(){
    serverGet();
    return 1;
}
