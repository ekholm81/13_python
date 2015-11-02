#include <iostream>
#include <unordered_set>
#include <bitset>
#include <vector>
#include <cmath>
#include <queue>

using namespace std;

typedef uint32_t Int;

const string EKHOLM_FILE_PATH = "/home/mq/Documents/vlad/i.txt";
const unsigned ROWS = 8;
const unsigned COLS = 3;
const size_t BITS_SIZE=ROWS*COLS;
const size_t BITSET_SIZE = sizeof(Int)*8;
vector<Int> BITS;


vector<unordered_set<Int> > av(ROWS*COLS+2); //avaible, remaining numbers by bitsize

uint8_t count_bits(const Int& val){
 bitset<BITSET_SIZE> b(val);
 return b.count();
}

void remove(const Int& val, uint8_t bits=-1);

//bfs
void choose(const Int& root_val, uint8_t root_bits=-1){
  queue<pair<Int, uint8_t> > Q;
  Q.push(make_pair(root_val, root_bits));
  while(!Q.empty()){
    Int val = Q.front().first;
    uint8_t bits = Q.front().second;
    Q.pop();
    if(bits==(uint8_t)(-1)) bits=count_bits(val);
    if(av[bits].find(val)==av[bits].end()) continue;
    //cout << "choose : " << val << "\n";
    remove(val, bits);
    for(unsigned i = 0; i<BITS_SIZE; ++i)
      if((val|BITS[i])==val) {
        //cout << "choosing : [" <<  val << "] " << val-BITS[i] << "\n";
        Q.push(make_pair(val-BITS[i], bits-1));
      }
  }
}


//dfs
void remove(const Int& val, uint8_t bits){
  if(bits==(uint8_t)(-1)) bits=count_bits(val);
  if(av[bits].find(val)==av[bits].end()) return;
  av[bits].erase(val);
  for(unsigned i = 0; i<BITS_SIZE; ++i) {
    const Int& next = val|BITS[i];
    if(next!=val) remove(next,bits+1);
  }
}

//dfs
void add(const Int& val, uint8_t bits){
  // cout << "adding : " << val << " [ " << (int)bits << "]\n";
  if(bits==(uint8_t)(-1)) bits=count_bits(val);
  if(av[bits].find(val)!=av[bits].end()) return;
  av[bits].insert(val);
  for(unsigned i = 0; i<BITS_SIZE; ++i) {
    const Int& next = val|BITS[i];
    if(next!=val) add(next,bits+1);
  }
}

#include <fstream>
void process_ekholm_file(const string& file_name){
  cout << "processing ekholm file..\n";
  ifstream f(file_name);
  if(!f) {
    cout << " *** could not open file\n";
    return;
  }
  string line;
  while(f >> line){
    Int val = 0;
    if(line.size()!=ROWS) {
      cout << " *** invalid line in ekholm file\n";
      continue;
    }
    for(size_t i = 0; i<line.size(); ++i)
      val += BITS[i*COLS+line[i]-'0'];
    remove(val,ROWS);
  }
  cout << "done processing ekholm file\n";
}

int main(){
  for(unsigned i =0; i<BITS_SIZE; i++) BITS.emplace_back(1ULL << i);
  //populate inital array;
  cout << "populating rows..\n";
  vector<size_t> rows(ROWS);
  const size_t COMBINATIONS=pow(COLS,ROWS);
  vector<size_t> powers;
  for(size_t i = 0; i<ROWS+1; ++i) powers.emplace_back(pow(COLS,i));
  for(size_t i = 0; i<COMBINATIONS; ++i){
    Int val = 0;
    for(size_t row=0; row<ROWS; ++row) {
      const size_t& choice = (i%powers[row+1])/powers[row];
      val += 1 << (row*COLS+choice);
    }
    add(val,ROWS);
  }
  cout << "populated rows\n";
  process_ekholm_file(EKHOLM_FILE_PATH);
  //remove(0b001001001001001001001001);
  vector<Int> res;
  for(size_t i=BITS_SIZE; i>=ROWS; --i){
    if(av[i].empty()) continue;
    res.emplace_back(*av[i].begin());
    //cout << "chose : " << res.back() << "\n";
    choose(*av[i].begin(), i);
    ++i;
  }
  cout << "done [=\n\n";
  cout << "total m-systems : " << res.size() << "\n";
  for(const Int& val:res) {
    bitset<BITSET_SIZE> b(val);
    for(size_t row = 0; row<ROWS; ++row){
      for(size_t col = 0; col<COLS; ++col) printf("%s", b[row*COLS+col] ? "X" : "O");
      printf("\n");
    }
    printf("\n");
  }
  return 0;
}

