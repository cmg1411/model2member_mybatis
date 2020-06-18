package dao;

import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.ibatis.common.resources.Resources;

import model.MemberDTO;

public class MemberDAO {
	
	private static MemberDAO instance = new MemberDAO();
	
	public static MemberDAO getInstance() {
		return instance;
	}
	
	// SqlSession 객체 생성
	private SqlSession getSession() {
		SqlSession session = null;
		Reader reader = null;
		
		try {
			reader = Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(reader);
			session = sf.openSession(true); // true속성은 auto commit
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return session;
	}
	
	public int insert(MemberDTO member) throws Exception{
		int result = 0;
		SqlSession session = getSession();
		
		result = session.insert("member_insert", member);
		return result;
	}
	
	public int idcheck(String id) throws Exception{
		String id2=null;
		int result = 0;
		
		SqlSession session = getSession();
		id2 = session.selectOne("select_by_id", id);
		
		if(id2!=null) {
			result = 1;
		}else {
			result = -1;
		}
		
		return result;
	}
	
	public int memberAuth(String id, String passwd) throws Exception{
		int result = 0;
		Map<String, String> map = new HashMap<String, String>();
		MemberDTO dto = new MemberDTO();
		SqlSession session = getSession();
		
		map.put("id", id);
		map.put("passwd", passwd);
		
		dto = session.selectOne("member_Auth", map);
		
		if(dto!=null) {
			result = 1;
		}else{
			result=-1;
		}
		
		return result;
	}
	
	public MemberDTO getmember(String id) throws Exception{
		MemberDTO dto = new MemberDTO();
		SqlSession session = getSession();
		
		dto = session.selectOne("member_getmember", id);
		return dto;
	}
	
	//수정
	public int update(MemberDTO member) throws Exception{
		int result = 0;
		SqlSession session = getSession();
		
		result = session.insert("member_update", member);
		return result;
	}
	 
	//삭제
	public int delete(String id) throws Exception{
		int result = 0;
		SqlSession session = getSession();
		
		result = session.insert("member_delete", id);
		return result;
	}
}
