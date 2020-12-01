#include<bits/stdc++.h>
using namespace std;

bool isPrime(int x)
{
    if(x==1 || x==2)    return 1;
    for(int i=2;i<x;i++){
        if(x%i==0)
            return 0;
    }
    return 1;
}

vector<int> allFactors(int x)
{
    vector<int> ans;
    ans.push_back(1);
    for(int i=2;i<=x;i++){
        if(x%i==0)
            ans.push_back(i);
    }
    return ans;
}

int main()
{
    int r;
    cout<<"Enter the number of circular rows(r): ";
    cin>>r;
    cout<<"Enter r space separated integers denoting number of students in ith row \n";
    vector<int> s(r);

    for(int i=0;i<r;i++){
        cin>>s[i];
    }

    int Dh,Dw;
    cout<<"Enter height and width of Output Panel: ";
    cin>>Dh>>Dw;
    int minDimension = min(Dh,Dw);
    int k;
    if(minDimension >= r*100){
        k=r;
    }
    else{
        k=minDimension/100;
    }

    vector<int> G;
    for(int i=0;i<k;i++){
        int maxDivisions = i+1;
        int startRing = (i*r)/k;
        int endRing = (i+1)*r/k;
        int divisions=INT_MIN;
        int num_sectors;
        vector<int> factors;

        for(int j=startRing;j<endRing;j++){
            divisions=max(divisions,s[j]);
        }

        if(divisions<=maxDivisions){
            num_sectors=divisions;
        }

        else{
            if(isPrime(divisions))
                factors = allFactors(divisions+1);
            else
                factors = allFactors(divisions);
        }

        for(int k=0;k<factors.size();k++){
            if(factors[k]<=maxDivisions)
                num_sectors = factors[k];
            else
                break;
        }

        if(num_sectors*2 <= maxDivisions)
            num_sectors = maxDivisions;

        G.push_back(num_sectors);
    }

    cout<<"\n\n\nIn Output: \n\n";
    cout<<"Total Number of Rings: "<<k<<endl;
    cout<<"Number of divisions in each ring"<<endl;
    for(int i=0;i<G.size();i++){
        cout<<G[i]<<" ";
    }
    cout<<endl;
    return 0;
}