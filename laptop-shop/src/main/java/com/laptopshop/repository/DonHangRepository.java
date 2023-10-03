package com.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.laptopshop.entities.DonHang;
import com.laptopshop.entities.NguoiDung;

public interface DonHangRepository extends JpaRepository<DonHang, Long>, QuerydslPredicateExecutor<DonHang> {

	public List<DonHang> findByTrangThaiDonHangAndShipper(String trangThai, NguoiDung shipper);

	@Query(value = "select DATE_FORMAT(dh.ngayNhanHang, '%m') as month, "
			+ " DATE_FORMAT(dh.ngayNhanHang, '%Y') as year, sum(ct.soLuongNhanHang * ct.donGia) as total "
			+ " from DonHang dh, ChiTietDonHang ct"
			+ " where dh.id = ct.donHang.id and dh.trangThaiDonHang ='Hoàn thành'"
			+ " group by DATE_FORMAT(dh.ngayNhanHang, '%Y%m')"
			+ " order by year asc" )
	public List<Object> layDonHangTheoThangVaNam();
	
	public List<DonHang> findByNguoiDat(NguoiDung ng);
	
	public int countByTrangThaiDonHang(String trangThaiDonHang);
	
	//TuanNV:
	@Query(value = "SELECT nd.dia_chi FROM nguoi_dung nd WHERE id = 6", nativeQuery = true)
	public String getDiachiRepo();
	
	//TuanNV:Test HQL
	@Query("SELECT vt.tenVaiTro FROM NguoiDung nd JOIN nd.vaiTro vt WHERE nd.id = :idcantim")
	public String getTenVaiTroRepo(@Param("idcantim") Long idcantim);
	
	//TuanNV:Test native SQL tìm mã vai trò từ id_nguoidung
	@Query(value = "SELECT nd_vt.ma_vai_tro FROM nguoi_dung nd INNER JOIN nguoidung_vaitro nd_vt ON nd.id = nd_vt.ma_nguoi_dung WHERE nd.id = ?1", nativeQuery = true)
	public String getMaVaiTroRepo(Long nguoiDungId);	
}
