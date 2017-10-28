package pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserRedPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long redPacketId;
	private Long userID;
	private Double amount;
	private Timestamp grabTime;
	private String note;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRedPacketId() {
		return redPacketId;
	}
	public void setRedPacketId(Long redPacketId) {
		this.redPacketId = redPacketId;
	}
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Timestamp getGrabTime() {
		return grabTime;
	}
	public void setGrabTime(Timestamp grabTime) {
		this.grabTime = grabTime;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
