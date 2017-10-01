package cn.gson.oasys.model.dao.notedao;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import cn.gson.oasys.model.entity.note.Note;
import cn.gson.oasys.model.entity.note.Noteuser;
import cn.gson.oasys.model.entity.user.User;

@Repository
public interface NoteDao  extends JpaRepository<Note, Long>{
    
	
	//删除
	@Query("delete from Note n where n.noteId=?1 ")
	@Modifying
	Integer delete(long noteid);
	
	@Query("update Note n set n.catalogId=?1,n.typeId=?2,n.statusId=?3,"
			+ "n.title=?4,n.content=?5 where n.noteId=?6")
	@Modifying
	Integer updatecollect(Long catalogId,Long typeId,Long statusId,String title,String content,long noteId);
	
	@Query("update Note n set n.isCollected=?1 where n.noteId=?2")
	@Modifying
	Integer updatecollect(long isCollected,long noteId);
	
	//通过用户id查找
	@Query("FROM Note n WHERE  n.noteId in (SELECT r.noteId from Noteuser r where r.userId=?1)")
	List<Note> finduser(Long userid);
	
	//查找是否已经收藏
	@Query("from Note n where n.isCollected=?1 and n.noteId in (SELECT r.noteId from Noteuser r where r.userId=?2)")
	List<Note> findByIsCollected  (long isCollected,long userid);
	
	//查找目录
	@Query("from Note n where n.catalogId=?1 and n.noteId in (SELECT r.noteId from Noteuser r where r.userId=?2)")
	List<Note> findByCatalogId(long catalogId,long userid);
	
	//查找类型
	@Query("from Note n where n.typeId=?1 and n.noteId in (SELECT r.noteId from Noteuser r where r.userId=?2)")
	List<Note> findByTypeId(long typeId,long userid);
	
	//标题查找
	@Query("from Note n where n.title like %?1% "
			+ "and n.noteId in (SELECT r.noteId from Noteuser r where r.userId=?2)"
			+ "")
	List<Note> findBytitle(String title,long userid);
	
	//通过笔记id找到用户id
	@Query("from Noteuser nu where nu.noteId=?1 and nu.userId=?2")
	Noteuser finduserid(long noteid,Long userId);
}
