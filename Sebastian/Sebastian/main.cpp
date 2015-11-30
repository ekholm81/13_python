#include <iostream>
#include <cmath>
#include <vector>
#include <unordered_map>
#define UTD 0.65
#define UTD13 0.4
#define UTD12 0.15
#define UTD11 0.12
#define UTD10 0.25
using namespace std;

struct row{
    double ch13;
    double ch12;
    double ch11;
    double ch10;

    double cr13;
    double cr12;
    double cr11;
    double cr10;

    double val13;
    double val12;
    double val11;
    double val10;
};
vector <row>rvec;
vector <vector <int> >komb12;
vector <vector <int> >komb11;
vector <vector <int> >komb10;
unordered_map<int,vector<int>> toter;
unordered_map<string,int> todec;
    double odds[13][3]= {{2.34,3.5,3.5},
                        {3.11,3.32,2.65},
                        {2.91,3.35,2.79},
                        {2.74,3.3,3.02},
                        {1.79,3.58,6.16},
                        {2.45,3.4,3.35},
                        {2.27,3.47,3.69},
                        {1.82,4.04,4.94},
                        {2.36,3.42,3.53},
                        {2.45,3.32,3.43},
                        {1.36,5.35,12.55},
                        {2.53,3.43,3.19},
                        {3.08,3.43,2.61}};
/*double crossed[13][3]=  {{41.0,32.0,27.0},
                        {25.0,27.0,48.0},
                        {32.0,31.0,37.0},
                        {40.0,35.0,25.0},
                        {83.0,13.0,4.0},
                        {58.0,24.0,18.0},
                        {55.0,25.0,20.0},
                        {74.0,16.0,10.0},
                        {63.0,23.0,14.0},
                        {27.0,29.0,44.0},
                        {86.0,10.0,4.0},
                        {41.0,31.0,28.0},
                        {18.0,22.0,60.0}};
*/
//2015-11-17
double crossed[13][3]= {{43.0,30.0,27.0},
                        {27.0,27.0,46.0},
                        {31.0,31.0,38.0},
                        {37.0,35.0,28.0},
                        {75.0,18.0,7.0},
                        {54.0,25.0,21.0},
                        {53.0,25.0,22.0},
                        {70.0,19.0,11.0},
                        {58.0,24.0,18.0},
                        {31.0,29.0,40.0},
                        {87.0,10.0,3.0},
                        {41.0,31.0,28.0},
                        {22.0,24.0,54.0}};

string sub(int a,int b){
    if(a==0)return to_string(b);
    else if(b==0)return to_string(a);
    else if(a==1&&b==1)return "2";
    else if(a==2&b==1)return "0";
    else if(a==1&b==2)return "0";
    else if(a==2&&b==2)return "1";
}

void initmap(){
    for(int i=0;i<pow(3,13);i++){
       vector <int>tmp;
       vector <int>res;
       string s="";
       if(i==0){
         for(int j=0;j<13;j++)res.push_back(0);
         toter.emplace(i,res);
         s="0000000000000";
       }
       else{
          int value=i;
          while(value>0){
            tmp.push_back((value%3));
            value=value/3;
          }
          while(tmp.size()<13)tmp.push_back(0);
          int cc=0;
          for(int j=0;j<13;j++){
            if(tmp[12-j]==0)cc++;
            res.push_back(tmp[12-j]);
            char c='0'+tmp[12-j];
            s+=c;
          }toter.emplace(i,res);
          if(cc==12)komb12.push_back(res);
          if(cc==11)komb11.push_back(res);
          if(cc==10)komb10.push_back(res);
       }
       todec.emplace(s,i);
    }cout<<"...Hash table init done\n";
}
void initvec(){
 int co=0;
  for(int i=0;i<pow(3,13);i++){
    vector<int>ter=toter[i];
    row r;
    r.ch13=1;
    r.cr13=1;
    r.ch12=0;
    r.cr12=0;
    r.ch11=0;
    r.cr11=0;
    r.ch10=0;
    r.cr10=0;
    for(int j=0;j<13;j++)r.ch13=r.ch13/odds[j][ter[j]];
    for(int j=0;j<13;j++)r.cr13=0.01*r.cr13*crossed[j][ter[j]];
    r.val13=UTD*UTD13*r.ch13/(r.cr13);
    rvec.push_back(r);
    }cout<<"...Vector init done\n";
}

void pop(int fel){
    vector <vector <int> >komb;
    if(fel==1)komb=komb12;
    if(fel==2)komb=komb11;
    if(fel==3)komb=komb10;
    for(int i=0;i<pow(3,13);i++){
       // if(i!=todec["1100100122002"])continue;
        if(i%1600==0)cout<<i/16000<<"%\n";
        vector<int>Mrow=toter[i];
        for(int j=0;j<komb.size();j++){
            string s="";
            for(int k=0;k<13;k++)s+=sub(Mrow[k],komb[j][k]);
            rvec[i].cr12+=rvec[todec[s]].cr13;
        }
       // cout<<rvec[i].cr12<<"  ";
     //   cout<<5000000*rvec[i].cr12;
    }
}

void removeLow(int fel, double utd, double thresh){
    vector <vector <int> >komb;
    if(fel==1)komb=komb12;
    if(fel==2)komb=komb11;
    if(fel==3)komb=komb10;
    for(int j=0;j<komb.size();j++){
        string s;
        for(int k=0;k<13;k++)s+=komb[j][k];
        if(utd/rvec[todec[s]].cr11<thresh)cout<<"!\n";
    }
}


int main()
{
    initmap();
    initvec();
    removeLow(2,0.65*0.12,15);
    //pop(2);
    return 0;
}
