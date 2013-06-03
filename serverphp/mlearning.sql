-- phpMyAdmin SQL Dump
-- version 3.1.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 18, 2012 at 10:41 PM
-- Server version: 5.1.30
-- PHP Version: 5.2.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `mlearning`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE IF NOT EXISTS `account` (
  `user` varchar(10) NOT NULL,
  `password` varchar(15) NOT NULL,
  `tipe` varchar(15) NOT NULL,
  `nama` varchar(25) NOT NULL,
  `nis` varchar(15) NOT NULL,
  `email` varchar(35) NOT NULL,
  `kelas` varchar(15) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`user`, `password`, `tipe`, `nama`, `nis`, `email`, `kelas`) VALUES
('dana', 'uny', 'siswa', 'pradana setialana', '123456', 'dana@sma.com', 'X A'),
('isni', 'isni', 'siswa', 'isni paw', '2345678', 'jmap@56k.com', 'X B'),
('iwan', 'iwan', 'siswa', 'iwan iwan', '565766567', 'iwan@gmail.com', 'X A'),
('martin', 'martin', 'guru', 'martini sugatri', '767655', 'martin@gatri.com', ''),
('budi', 'budi', 'guru', 'budi utomo', '29837456', 'budi@gmail.com', 'X A');

-- --------------------------------------------------------

--
-- Table structure for table `daftar_kelas`
--

CREATE TABLE IF NOT EXISTS `daftar_kelas` (
  `kode_kls` varchar(6) NOT NULL,
  `nama` varchar(15) NOT NULL,
  `jurusan` varchar(8) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `daftar_kelas`
--

INSERT INTO `daftar_kelas` (`kode_kls`, `nama`, `jurusan`) VALUES
('10XA', 'X A', 'X'),
('10XB', 'X B', 'X'),
('11AA', 'XI IPA A', 'IPA'),
('12SB', 'XII IPS B', 'IPS');

-- --------------------------------------------------------

--
-- Table structure for table `daftar_mapel`
--

CREATE TABLE IF NOT EXISTS `daftar_mapel` (
  `kode_mapel` varchar(10) NOT NULL,
  `jurusan` varchar(6) NOT NULL,
  `nama` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `daftar_mapel`
--

INSERT INTO `daftar_mapel` (`kode_mapel`, `jurusan`, `nama`) VALUES
('MTK10', 'X', 'Matematika'),
('BIND10', 'X', 'Bahasa Indonesia'),
('FSK11', 'IPA', 'Fisika'),
('EKM12', 'IPS', 'Ekonomi');

-- --------------------------------------------------------

--
-- Table structure for table `materi`
--

CREATE TABLE IF NOT EXISTS `materi` (
  `kelas` varchar(10) NOT NULL,
  `pelajaran` varchar(20) NOT NULL,
  `judul` varchar(20) NOT NULL,
  `diskripsi` varchar(50) NOT NULL,
  `kategori` varchar(10) NOT NULL,
  `url` varchar(150) NOT NULL,
  `user` varchar(15) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `materi`
--

INSERT INTO `materi` (`kelas`, `pelajaran`, `judul`, `diskripsi`, `kategori`, `url`, `user`) VALUES
('X A', 'Matematika', 'tugas aljabar', 'jawaban soal aljabar lsk halaman 3', 'Tugas', 'http://unymlearning.comlu.com/android/01_intro-LEN.pdf', 'dana'),
('X A', 'Matematika', 'aljabar dasar', 'aljabar matematika dasar dan awal, mengulang waktu', 'Materi', 'http://unymlearning.comlu.com/android/Analisis double linked list.docx', 'budi'),
('X A', 'Matematika', 'integral', 'memahami integral dasar', 'Materi', 'http://unymlearning.comlu.com/android/01_intro-LEN.pdf', 'budi'),
('XI IPA A', 'Kimia', 'tabel periodik', 'memahamai cara membaca tabel periodik ', 'Materi', 'http://unymlearning.comlu.com/android/Analisis double linked list.docx', 'martin'),
('X A', 'Matematika', 'tugas aljabar dasar ', 'tugas halaman 3', 'Tugas', 'http://unymlearning.comlu.com/android/01_intro-LEN.pdf', 'iwan'),
('X A', 'Matematika', 'pytagoras', 'rumus-rumus pytagoras', 'Materi', 'http://unymlearning.comlu.com/android/Analisis double linked list.docx', 'budi'),
('X A', 'Matematika', 'tugas pytagoras', 'soal halaman 2', 'Tugas', 'http://unymlearning.comlu.com/android/01_intro-LEN.pdf', 'dana'),
('XI IPA A', 'Kimia', 'unsur dan senyawa', 'mempelajari perbedaan unsur dan senyawa', 'Materi', 'http://unymlearning.comlu.com/android/pidato.docx', 'martin'),
('XI IPA A', 'Kimia', 'ikatan antar atom', 'mempelajari ikatan antar atom hingga membentuk sen', 'Materi', 'http://unymlearning.comlu.com/android/Heal The World.docx', 'martin');
