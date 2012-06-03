#include <cstdlib>
#include <iostream>
#include <fstream>

using namespace std;

int main()
{
    int count=300;
    ofstream os("1.txt");
    os<<count<<endl;
    for(int i=0; i<count; ++i)
        os<<rand()%7<<endl;
    os.close();
    cout<<"done!"<<endl;
}
