package smartcity.accessibility.services;

import java.util.List;

import smartcity.accessibility.search.SearchQuery;

public class wrapper{
	String res;
	List<SearchQuery> res2;
	
	public wrapper(String res){
		this.res = res;
	}
	
	public wrapper(List<SearchQuery> lsq){
		this.res2 = lsq;
	}
	
	public String getRes(){
		return this.res;
	}
	
	public List<SearchQuery> getRes2(){
		return this.res2;
	}
};
