#include <iostream>
#include <string>
#include <stdio.h>
#include <time.h>
#include <vector>
#include <algorithm>    // std::random_shuffle
#include <fstream>
#include <cstdlib>      // std::rand, std::srand
using namespace std;
int glob_r;
string currentDateTime() {
    time_t     now = time(0);
    struct tm  tstruct;
    char       buf[80];
    tstruct = *localtime(&now);
    // Visit http://en.cppreference.com/w/cpp/chrono/c/strftime
    // for more information about date/time format
    strftime(buf, sizeof(buf), "%Y-%m-%d.%X", &tstruct);

    return buf;
}

bool same_sign(int a, int b){
    if (b==0)
		return (a==0 || a==3 || a==4 || a==6);
	if (b==1)
		return (a==1 || a==3 || a==5 || a==6);
	if (b==2)
		return (a==2 || a==4 || a==5 || a==6);
}

bool same_m_sign(int a,int b){
    if(b==6)
        return true;
    if(b==5)
        return (a==1 || a==2 || a==5);
    if(b==4)return (a==0||a==2||a==4);
    if(b==3)return (a==0||a==1||a==3);
    return (a==b);
}

bool in_row(const vector<int>&msys,const vector<int>&row){
    if(!same_sign(msys[0],row[0]))return false;
    if(!same_sign(msys[1],row[1]))return false;
    if(!same_sign(msys[2],row[2]))return false;
    if(!same_sign(msys[3],row[3]))return false;
    if(!same_sign(msys[4],row[4]))return false;
    if(!same_sign(msys[5],row[5]))return false;
    if(!same_sign(msys[6],row[6]))return false;
    if(!same_sign(msys[7],row[7]))return false;
    return true;
}

bool in_mrow(const vector<int>&msys,const vector<int>&row){
    if(!same_m_sign(msys[0],row[0]))return false;
    if(!same_m_sign(msys[1],row[1]))return false;
    if(!same_m_sign(msys[2],row[2]))return false;
    if(!same_m_sign(msys[3],row[3]))return false;
    if(!same_m_sign(msys[4],row[4]))return false;
    if(!same_m_sign(msys[5],row[5]))return false;
    if(!same_m_sign(msys[6],row[6]))return false;
    if(!same_m_sign(msys[7],row[7]))return false;
    return true;
}

void print_row(vector<int>&r,std::ofstream& dumFile,int sz,int sel){
    cout << "vec: ";
    for(int i=0;i<8;i++){
        dumFile<<r[i];
        cout<<r[i]<<"-";
    }
    cout<<"\n";
    dumFile<<"  "<<sz<<"  "<<sel<<"\n";
}

void set_rrows(const vector<int>&msys,vector<vector<int> >&rrows){
    vector< vector<int> >rows;
    vector<int>tmp(8);
    for(int a=0;a<3;a++)for(int b=0;b<3;b++)for(int c=0;c<3;c++)for(int d=0;d<3;d++)for(int e=0;e<3;e++)for(int f=0;f<3;f++)for(int g=0;g<3;g++)for(int h=0;h<3;h++){
        tmp[0]=a;
        tmp[1]=b;
        tmp[2]=c;
        tmp[3]=d;
        tmp[4]=e;
        tmp[5]=f;
        tmp[6]=g;
        tmp[7]=h;
        rows.push_back(tmp);
    }
        for (int j=0;j<rows.size();j++){
            if(in_row(msys,rows[j])){
                rrows.push_back(rows[j]);
            }
        }
}

int get_sign_val(int a){
    if(a==0||a==1||a==2)return 1;
    if(a==6)return 3;
    return 2;
}

void trim_msys(vector<vector<int> >&msys,const vector<int>&m){
    int d=0;
    for (int i=0;i<msys.size();i++){
        if(in_mrow(m,msys[i-d])){
            swap(msys[i-d],msys[msys.size()-1]);
            msys.pop_back();
            d++;
        }
    }
}

int find_lg_m(vector<vector<int> >&m){
    int lg=0;
    int idx=-1;
    for(int i=0;i<m.size();i++){
        int r=1;
        for(int j=0;j<8;j++){
            r*=get_sign_val(m[i][j]);
        }
        if(r>lg){
            lg=r;
            idx=i;
        }
    }
    return idx;
}

int main()
{
    glob_r=0;
    ofstream myfile;
    myfile.open ("o.txt");

    int sel=0;
    vector<int>tmp(8);
    vector< vector<int> >msys;
    vector< vector<int> >chosen;
    for(int a=0;a<7;a++)for(int b=0;b<7;b++)for(int c=0;c<7;c++)for(int d=0;d<7;d++)for(int e=0;e<7;e++)for(int f=0;f<7;f++)for(int g=0;g<7;g++)for(int h=0;h<7;h++){
        tmp[0]=a;
        tmp[1]=b;
        tmp[2]=c;
        tmp[3]=d;
        tmp[4]=e;
        tmp[5]=f;
        tmp[6]=g;
        tmp[7]=h;
        msys.push_back(tmp);
   }
    vector< vector<int> >rrows;

  /*  for(int a=0;a<3;a++)for(int b=0;b<1;b++)for(int c=0;c<2;c++)for(int d=0;d<3;d++)for(int e=0;e<1;e++)for(int f=0;f<3;f++)for(int g=0;g<1;g++)for(int h=0;h<1;h++){

        tmp[0]=a;
        tmp[1]=b;
        tmp[2]=c;
        tmp[3]=d;
        tmp[4]=e;
        tmp[5]=f;
        tmp[6]=g;
        tmp[7]=h;
        rrows.push_back(tmp);
    }*/
    ifstream infile("i.txt");
    string line;
    while (std::getline(infile, line))
    {
        for (int i=0;i<line.size();i++){
            tmp[i]=line[i]-48;
        }
        rrows.push_back(tmp);
    }
    random_shuffle(rrows.begin(),rrows.end());
    cout <<rrows.size()<<"\n";
    for (int i=0;i<rrows.size();i++){
        cout<<msys.size()<<" : "<<i<<"\n";
        int it=msys.size();
        int d=0;
        for(int j=0;j<it;j++){
            if(in_row(msys[j-d],rrows[i])){
                swap(msys[j-d],msys[msys.size()-1]);
                msys.pop_back();
                d++;
            }
        }
    }
//    ofstream myfile;
//    myfile.open ("rows.txt");
    while(true){
        if(msys.size()==0){
            cout<<"terminating";
             myfile.close();
            break;
        }
        int idx=find_lg_m(msys);
        chosen.push_back(msys[idx]);
        rrows.clear();
        set_rrows(msys[idx],rrows);
        if(rrows.size()==0)break;
        sel+=rrows.size();
        print_row(msys[idx],myfile,rrows.size(),sel);
        cout<<"sel: "<<sel<<"\n";
    //    trim_msys(msys,msys[idx]);
        cout <<"msys sz: "<<msys.size()<<"\n";
        cout <<"rrows sz: "<<rrows.size()<<"\n";
        for (int i=0;i<rrows.size();i++){
            if(i%100==0)cout<<i<<" msys sz: "<<msys.size()<<"\n";
            int it=msys.size();
            int d=0;
            for(int j=0;j<it;j++){
                if(in_row(msys[j-d],rrows[i])){
                    swap(msys[j-d],msys[msys.size()-1]);
                    msys.pop_back();
                    d++;
                }
            }
        }
    }
    int a;
    cin>>a;
    return 0;
}
