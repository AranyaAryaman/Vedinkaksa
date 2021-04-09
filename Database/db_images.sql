-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 09, 2021 at 09:06 AM
-- Server version: 10.5.9-MariaDB-1
-- PHP Version: 7.4.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_images`
--
CREATE DATABASE IF NOT EXISTS `db_images` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `db_images`;

-- --------------------------------------------------------

--
-- Table structure for table `001`
--

DROP TABLE IF EXISTS `001`;
CREATE TABLE IF NOT EXISTS `001` (
  `Timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `Mental_State` text NOT NULL,
  `Visualization_State` text NOT NULL,
  `alert` int(11) DEFAULT NULL,
  `alert_count` int(11) NOT NULL,
  `alert_acted_count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `160101071`
--

DROP TABLE IF EXISTS `160101071`;
CREATE TABLE IF NOT EXISTS `160101071` (
  `Time` datetime NOT NULL,
  `Mental_State` text NOT NULL,
  `Derived_State` text NOT NULL,
  `alert` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `attendance_records`
--

DROP TABLE IF EXISTS `attendance_records`;
CREATE TABLE IF NOT EXISTS `attendance_records` (
  `username` varchar(20) NOT NULL,
  `attendance_time` timestamp NOT NULL DEFAULT current_timestamp(),
  `class` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `attendance_records`
--

INSERT INTO `attendance_records` (`username`, `attendance_time`, `class`) VALUES
('170101015', '2021-04-06 10:27:12', 'CS');

-- --------------------------------------------------------

--
-- Table structure for table `attendance_status`
--

DROP TABLE IF EXISTS `attendance_status`;
CREATE TABLE IF NOT EXISTS `attendance_status` (
  `status` varchar(8) NOT NULL DEFAULT 'false',
  `class` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `attendance_status`
--

INSERT INTO `attendance_status` (`status`, `class`) VALUES
('false', 'CS');

-- --------------------------------------------------------

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
CREATE TABLE IF NOT EXISTS `images` (
  `id` varchar(255) NOT NULL,
  `url` text NOT NULL,
  `name` text NOT NULL,
  `roll` varchar(50) NOT NULL,
  `pass` text NOT NULL,
  PRIMARY KEY (`roll`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `images`
--

INSERT INTO `images` (`id`, `url`, `name`, `roll`, `pass`) VALUES
('001', 'http://localhost/Sites/Server/CoreFunctionality/uploads/001.jpg', 'animesh', '001', 'animesh'),
('160101071', 'http://localhost/Sites/Server/CoreFunctionality/uploads/160101071.jpeg', 'unable error', '160101071', '123'),
('160101072', 'http://localhost/Sites/Server/CoreFunctionality/uploads/160101072.jpeg', '160101072', '160101072', '123456'),
('166101007', 'http://localhost/Sites/Server/CoreFunctionality/uploads/166101007.jpg', 'Ujjwal Biswas', '166101007', 'ubiswas'),
('170101015', 'http://localhost/Sites/Server/CoreFunctionality/uploads/170101015.jpg', 'Avneet', '170101015', 'Avneet'),
('asus', 'http://localhost/Sites/Server/CoreFunctionality/uploads/asus.jpeg', 'asus', 'asus', '123456'),
('htc', 'http://localhost/Sites/Server/CoreFunctionality/uploads/htc.jpeg', 'htc', 'htc', '123456'),
('IITG100', '', 'sid', 'IITG100', '100'),
('moto', 'http://localhost/Sites/Server/CoreFunctionality/uploads/moto.jpeg', 'moto', 'moto', '123456'),
('samsung', 'http://localhost/Sites/Server/CoreFunctionality/uploads/samsung.jpeg', 'samsung', 'samsung', '123456'),
('sony1', 'http://localhost/Sites/Server/CoreFunctionality/uploads/sony1.jpeg', 'sony1', 'sony1', '123456'),
('sony2', 'http://localhost/Sites/Server/CoreFunctionality/uploads/sony2.jpeg', 'sony2', 'sony2', '123456');

-- --------------------------------------------------------

--
-- Table structure for table `image_tbl`
--

DROP TABLE IF EXISTS `image_tbl`;
CREATE TABLE IF NOT EXISTS `image_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roll` text NOT NULL,
  `image_url` text NOT NULL,
  `PosX` int(11) NOT NULL,
  `PosY` int(11) NOT NULL,
  `State_in` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `image_tbl`
--

INSERT INTO `image_tbl` (`id`, `roll`, `image_url`, `PosX`, `PosY`, `State_in`) VALUES
(1, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 1, 'DS0'),
(2, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 2, 'DS0'),
(3, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 3, 'DS0'),
(4, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 4, 'DS0'),
(5, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 5, 'DS0'),
(6, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 6, 'DS0'),
(7, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 7, 'DS0'),
(8, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 8, 'DS0'),
(9, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 9, 'DS0'),
(10, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 10, 'DS0'),
(11, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 11, 'DS0'),
(12, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 12, 'DS0'),
(13, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 13, 'DS0'),
(14, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 14, 'DS0'),
(15, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 15, 'DS0'),
(16, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 16, 'DS0'),
(17, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 17, 'DS0'),
(18, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 18, 'DS0'),
(19, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 19, 'DS0'),
(20, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 1, 20, 'DS0'),
(21, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 1, 'DS0'),
(22, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 2, 'DS0'),
(23, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 3, 'DS0'),
(24, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 4, 'DS0'),
(25, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 5, 'DS0'),
(26, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 6, 'DS0'),
(27, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 7, 'DS0'),
(28, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 8, 'DS0'),
(29, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 9, 'DS0'),
(30, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 10, 'DS0'),
(31, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 11, 'DS0'),
(32, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 12, 'DS0'),
(33, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 13, 'DS0'),
(34, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 14, 'DS0'),
(35, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 15, 'DS0'),
(36, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 16, 'DS0'),
(37, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 17, 'DS0'),
(38, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 18, 'DS0'),
(39, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 19, 'DS0'),
(40, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 2, 20, 'DS0'),
(41, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 1, 'DS0'),
(42, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 2, 'DS0'),
(43, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 3, 'DS0'),
(44, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 4, 'DS0'),
(45, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 5, 'DS0'),
(46, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 6, 'DS0'),
(47, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 7, 'DS0'),
(48, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 8, 'DS0'),
(49, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 9, 'DS0'),
(50, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 10, 'DS0'),
(51, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 11, 'DS0'),
(52, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 12, 'DS0'),
(53, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 13, 'DS0'),
(54, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 14, 'DS0'),
(55, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 15, 'DS0'),
(56, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 16, 'DS0'),
(57, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 17, 'DS0'),
(58, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 18, 'DS0'),
(59, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 19, 'DS0'),
(60, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 3, 20, 'DS0'),
(61, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 1, 'DS0'),
(62, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 2, 'DS0'),
(63, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 3, 'DS0'),
(64, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 4, 'DS0'),
(65, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 5, 'DS0'),
(66, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 6, 'DS0'),
(67, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 7, 'DS0'),
(68, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 8, 'DS0'),
(69, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 9, 'DS0'),
(70, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 10, 'DS0'),
(71, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 11, 'DS0'),
(72, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 12, 'DS0'),
(73, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 13, 'DS0'),
(74, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 14, 'DS0'),
(75, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 15, 'DS0'),
(76, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 16, 'DS0'),
(77, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 17, 'DS0'),
(78, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 18, 'DS0'),
(79, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 19, 'DS0'),
(80, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 4, 20, 'DS0'),
(81, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 1, 'DS0'),
(82, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 2, 'DS0'),
(83, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 3, 'DS0'),
(84, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 4, 'DS0'),
(85, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 5, 'DS0'),
(86, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 6, 'DS0'),
(87, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 7, 'DS0'),
(88, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 8, 'DS0'),
(89, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 9, 'DS0'),
(90, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 10, 'DS0'),
(91, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 11, 'DS0'),
(92, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 12, 'DS0'),
(93, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 13, 'DS0'),
(94, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 14, 'DS0'),
(95, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 15, 'DS0'),
(96, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 16, 'DS0'),
(97, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 17, 'DS0'),
(98, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 18, 'DS0'),
(99, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 19, 'DS0'),
(100, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 5, 20, 'DS0'),
(101, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 6, 1, 'DS0'),
(102, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 6, 2, 'DS0'),
(103, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 6, 3, 'DS0'),
(104, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 6, 4, 'DS0'),
(105, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 6, 5, 'DS0'),
(106, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 6, 6, 'DS0'),
(107, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 6, 7, 'DS0'),
(108, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 6, 8, 'DS0'),
(109, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 6, 9, 'DS0'),
(110, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 6, 10, 'DS0'),
(111, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 6, 11, 'DS0'),
(112, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 6, 12, 'DS0'),
(113, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 6, 13, 'DS0'),
(114, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 6, 14, 'DS0'),
(115, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 6, 15, 'DS0'),
(116, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 6, 16, 'DS0'),
(117, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 6, 17, 'DS0'),
(118, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 6, 18, 'DS0'),
(119, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 6, 19, 'DS0'),
(120, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 6, 20, 'DS0'),
(121, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 7, 1, 'DS0'),
(122, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 7, 2, 'DS0'),
(123, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 7, 3, 'DS0'),
(124, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 7, 4, 'DS0'),
(125, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 7, 5, 'DS0'),
(126, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 7, 6, 'DS0'),
(127, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 7, 7, 'DS0'),
(128, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 7, 8, 'DS0'),
(129, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 7, 9, 'DS0'),
(130, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 7, 10, 'DS0'),
(131, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 7, 11, 'DS0'),
(132, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 7, 12, 'DS0'),
(133, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 7, 13, 'DS0'),
(134, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 7, 14, 'DS0'),
(135, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 7, 15, 'DS0'),
(136, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 7, 16, 'DS0'),
(137, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 7, 17, 'DS0'),
(138, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 7, 18, 'DS0'),
(139, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 7, 19, 'DS0'),
(140, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 7, 20, 'DS0'),
(141, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 8, 1, 'DS0'),
(142, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 8, 2, 'DS0'),
(143, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 8, 3, 'DS0'),
(144, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 8, 4, 'DS0'),
(145, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 8, 5, 'DS0'),
(146, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 8, 6, 'DS0'),
(147, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 8, 7, 'DS0'),
(148, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 8, 8, 'DS0'),
(149, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 8, 9, 'DS0'),
(150, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 8, 10, 'DS0'),
(151, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 8, 11, 'DS0'),
(152, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 8, 12, 'DS0'),
(153, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 8, 13, 'DS0'),
(154, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 8, 14, 'DS0'),
(155, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 8, 15, 'DS0'),
(156, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 8, 16, 'DS0'),
(157, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 8, 17, 'DS0'),
(158, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 8, 18, 'DS0'),
(159, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 8, 19, 'DS0'),
(160, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 8, 20, 'DS0'),
(161, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 9, 1, 'DS0'),
(162, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 9, 2, 'DS0'),
(163, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 9, 3, 'DS0'),
(164, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 9, 4, 'DS0'),
(165, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 9, 5, 'DS0'),
(166, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 9, 6, 'DS0'),
(167, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 9, 7, 'DS0'),
(168, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 9, 8, 'DS0'),
(169, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 9, 9, 'DS0'),
(170, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 9, 10, 'DS0'),
(171, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 9, 11, 'DS0'),
(172, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 9, 12, 'DS0'),
(173, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 9, 13, 'DS0'),
(174, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 9, 14, 'DS0'),
(175, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 9, 15, 'DS0'),
(176, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 9, 16, 'DS0'),
(177, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 9, 17, 'DS0'),
(178, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 9, 18, 'DS0'),
(179, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 9, 19, 'DS0'),
(180, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 9, 20, 'DS0'),
(181, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 10, 1, 'DS0'),
(182, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 10, 2, 'DS0'),
(183, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 10, 3, 'DS0'),
(184, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 10, 4, 'DS0'),
(185, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 10, 5, 'DS0'),
(186, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 10, 6, 'DS0'),
(187, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 10, 7, 'DS0'),
(188, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 10, 8, 'DS0'),
(189, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 10, 9, 'DS0'),
(190, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 10, 10, 'DS0'),
(191, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 10, 11, 'DS0'),
(192, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 10, 12, 'DS0'),
(193, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 10, 13, 'DS0'),
(194, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 10, 14, 'DS0'),
(195, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 10, 15, 'DS0'),
(196, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 10, 16, 'DS0'),
(197, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 10, 17, 'DS0'),
(198, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 10, 18, 'DS0'),
(199, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 10, 19, 'DS0'),
(200, 'empty', 'http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg', 10, 20, 'DS0');

-- --------------------------------------------------------

--
-- Table structure for table `queries`
--

DROP TABLE IF EXISTS `queries`;
CREATE TABLE IF NOT EXISTS `queries` (
  `q_id` int(11) NOT NULL AUTO_INCREMENT,
  `query` text NOT NULL,
  `page` int(11) NOT NULL,
  `slide` text NOT NULL,
  `likes` int(11) NOT NULL DEFAULT 0,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp(),
  `is_answered` int(11) NOT NULL DEFAULT 0,
  `answer` text DEFAULT NULL,
  `answer_added` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`q_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Seat_arrangement`
--

DROP TABLE IF EXISTS `Seat_arrangement`;
CREATE TABLE IF NOT EXISTS `Seat_arrangement` (
  `student_id` varchar(10) NOT NULL,
  `seat_row` int(11) NOT NULL,
  `seat_column` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Seat_arrangement`
--

INSERT INTO `Seat_arrangement` (`student_id`, `seat_row`, `seat_column`) VALUES
('001', 1, 1),
('160101071', 1, 2),
('asus', 1, 3),
('htc', 2, 1),
('moto', 2, 2),
('samsung', 2, 3),
('sony2', 3, 1),
('sony1', 3, 2),
('160101072', 3, 3),
('170101015', 4, 3);

-- --------------------------------------------------------

--
-- Table structure for table `slides`
--

DROP TABLE IF EXISTS `slides`;
CREATE TABLE IF NOT EXISTS `slides` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` text NOT NULL,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `slides`
--

INSERT INTO `slides` (`id`, `path`, `name`) VALUES
(16, 'http://localhost/Sites/Server/AndroidImageUpload/uploads/siddharth.pdf', 'siddharth.pdf'),
(17, 'http://localhost/Sites/Server/AndroidImageUpload/uploads/networks.pdf', 'networks.pdf'),
(18, 'http://localhost/Sites/Server/AndroidImageUpload/uploads/stackoverflow.pdf', 'stackoverflow.pdf'),
(19, 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploadstest1.pdf', 'test1.pdf'),
(20, 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploadstest2.pdf', 'test2.pdf'),
(21, 'http://localhost/Sites/Server/CoreFunctionality/uploads/hello.pdf', 'hello.pdf'),
(22, 'http://localhost/Sites/Server/CoreFunctionality/uploads/hello.pdf', 'hello.pdf'),
(23, 'http://localhost/Sites/Server/CoreFunctionality/uploads/hello.pdf', 'hello.pdf'),
(24, 'http://localhost/Sites/Server/CoreFunctionality/uploads/hello.pdf', 'hello.pdf'),
(25, 'http://localhost/Sites/Server/CoreFunctionality/uploads/hello.pdf', 'hello.pdf'),
(26, 'http://localhost/Sites/Server/CoreFunctionality/uploads/hello.pdf', 'hello.pdf'),
(27, 'http://localhost/Sites/Server/CoreFunctionality/uploads/806093406.pdf', '806093406.pdf'),
(28, 'http://localhost/Sites/Server/CoreFunctionality/uploads/ascaws.pdf', 'ascaws.pdf'),
(29, 'http://localhost/Sites/Server/CoreFunctionality/uploads/ascaws.pdf', 'ascaws.pdf'),
(30, 'http://localhost/Sites/Server/CoreFunctionality/uploads/ugycuhufih.pdf', 'ugycuhufih.pdf'),
(31, 'http://localhost/Sites/Server/CoreFunctionality/uploads/2015.pdf', '2015.pdf'),
(32, 'http://localhost/Sites/Server/CoreFunctionality/uploads/pone.0237073.pdf', 'pone.0237073.pdf'),
(33, 'http://localhost/Sites/Server/CoreFunctionality/uploads/vevev.pdf', 'vevev.pdf'),
(34, 'http://localhost/Sites/Server/CoreFunctionality/uploads/2015.pdf', '2015.pdf'),
(35, 'http://localhost/Sites/Server/CoreFunctionality/uploads/vevev.pdf', 'vevev.pdf'),
(36, 'http://localhost/Sites/Server/CoreFunctionality/uploads/806093406.pdf', '806093406.pdf');

-- --------------------------------------------------------

--
-- Table structure for table `slide_sync`
--

DROP TABLE IF EXISTS `slide_sync`;
CREATE TABLE IF NOT EXISTS `slide_sync` (
  `name` varchar(20) NOT NULL,
  `page` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `slide_sync`
--

INSERT INTO `slide_sync` (`name`, `page`) VALUES
('', 0);

-- --------------------------------------------------------

--
-- Table structure for table `State_Calc`
--

DROP TABLE IF EXISTS `State_Calc`;
CREATE TABLE IF NOT EXISTS `State_Calc` (
  `timestamp` datetime NOT NULL,
  `roll` varchar(50) NOT NULL,
  `involvement` int(11) NOT NULL,
  `Arousal` int(11) NOT NULL,
  `Valence` int(11) NOT NULL,
  `Arousal_Valence_state` int(11) NOT NULL,
  `Classroom_Activity_State` int(11) NOT NULL,
  `Mental_State` text NOT NULL,
  PRIMARY KEY (`roll`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `State_Calc`
--

INSERT INTO `State_Calc` (`timestamp`, `roll`, `involvement`, `Arousal`, `Valence`, `Arousal_Valence_state`, `Classroom_Activity_State`, `Mental_State`) VALUES
('2018-07-06 23:18:55', 'siddhi', 1, 1, 1, 1, 1, 's1');

-- --------------------------------------------------------

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
CREATE TABLE IF NOT EXISTS `teacher` (
  `Timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `AlertType` int(11) NOT NULL,
  `Alert_count_not_engaged` int(11) NOT NULL,
  `Alert_count_not_understanding` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `temp_states`
--

DROP TABLE IF EXISTS `temp_states`;
CREATE TABLE IF NOT EXISTS `temp_states` (
  `student_id` varchar(10) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `Involvement` int(11) NOT NULL,
  `Activity` int(11) NOT NULL,
  `Arousal` int(11) NOT NULL,
  `Valence` int(11) NOT NULL,
  `State` int(11) NOT NULL,
  `Mental_State` int(11) NOT NULL,
  `Visualization_State` int(11) NOT NULL,
  `Alert` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `temp_states`
--

INSERT INTO `temp_states` (`student_id`, `timestamp`, `Involvement`, `Activity`, `Arousal`, `Valence`, `State`, `Mental_State`, `Visualization_State`, `Alert`) VALUES
('1', '2021-04-06 11:39:18', 1, 1, 2, 2, 1, 1, 1, 1),
('htc', '2021-04-06 11:38:14', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:06:41', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:06:48', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:06:55', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:07:02', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:07:09', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:07:16', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:07:23', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:07:30', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:07:37', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:07:44', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:07:51', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:07:58', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:08:05', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:08:12', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:08:19', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:08:26', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:08:33', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:08:40', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:08:47', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:08:54', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:09:01', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:09:08', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:09:15', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:09:22', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:09:29', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:09:36', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:09:43', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:09:50', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:09:57', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:10:04', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:10:11', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:10:18', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:10:25', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:10:32', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:10:39', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:10:46', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:10:53', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:11:00', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:11:07', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:11:14', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-06 12:11:21', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:31:18', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:31:25', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:31:32', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:31:39', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:31:46', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:31:53', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:32:00', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:32:07', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:32:14', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:32:21', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:32:28', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:32:35', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:32:42', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:32:49', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:32:56', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:33:03', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:33:10', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:33:17', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:33:24', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:33:31', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:33:38', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:33:45', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:33:52', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:33:59', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:34:06', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:34:13', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:34:20', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:34:27', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:34:34', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:34:41', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:34:48', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:34:55', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:35:02', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:35:49', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:35:56', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:36:03', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:36:10', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:36:17', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:36:24', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:36:31', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:36:38', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:36:45', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:36:52', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:36:59', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:37:06', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:37:13', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:37:20', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:37:27', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:37:34', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:37:41', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:37:48', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:37:55', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:38:02', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:38:09', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:38:16', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:38:23', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:38:30', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:38:37', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:38:44', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:38:51', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:38:58', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:39:05', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:39:12', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:39:19', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:39:26', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:39:33', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:39:40', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:39:47', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:39:54', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:40:01', 1, 1, 0, 0, 0, 5, 1, 1),
('htc', '2021-04-08 10:44:40', 1, 1, 0, 0, 0, 5, 1, 1),
('moto', '2021-04-08 10:48:41', 1, 1, 0, 0, 0, 5, 1, 1),
('moto', '2021-04-08 10:48:48', 1, 1, 0, 0, 0, 5, 1, 1),
('moto', '2021-04-08 10:48:55', 1, 1, 0, 0, 0, 5, 1, 1),
('moto', '2021-04-08 10:49:02', 1, 1, 0, 0, 0, 5, 1, 1),
('moto', '2021-04-08 10:49:09', 1, 1, 0, 0, 0, 5, 1, 1),
('moto', '2021-04-08 10:49:16', 1, 1, 0, 0, 0, 5, 1, 1),
('moto', '2021-04-08 10:49:23', 1, 1, 0, 0, 0, 5, 1, 1),
('moto', '2021-04-08 10:49:30', 1, 1, 0, 0, 0, 5, 1, 1),
('moto', '2021-04-08 10:49:37', 1, 1, 0, 0, 0, 5, 1, 1),
('moto', '2021-04-08 10:49:44', 1, 1, 0, 0, 0, 5, 1, 1),
('moto', '2021-04-08 10:49:51', 1, 1, 0, 0, 0, 5, 1, 1),
('moto', '2021-04-08 10:49:58', 1, 1, 0, 0, 0, 5, 1, 1),
('moto', '2021-04-08 10:50:05', 1, 1, 0, 0, 0, 5, 1, 1),
('moto', '2021-04-08 10:50:12', 1, 1, 0, 0, 0, 5, 1, 1),
('moto', '2021-04-08 10:50:19', 1, 1, 0, 0, 0, 5, 1, 1),
('moto', '2021-04-08 10:50:26', 1, 1, 0, 0, 0, 5, 1, 1),
('moto', '2021-04-08 10:50:33', 1, 1, 0, 0, 0, 5, 1, 1),
('moto', '2021-04-08 10:50:40', 1, 1, 0, 0, 0, 5, 1, 1),
('moto', '2021-04-08 10:50:47', 1, 1, 0, 0, 0, 5, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `Visualisation_States`
--

DROP TABLE IF EXISTS `Visualisation_States`;
CREATE TABLE IF NOT EXISTS `Visualisation_States` (
  `timestamp` datetime NOT NULL,
  `roll` text NOT NULL,
  `Derived_States` int(11) NOT NULL,
  `Alert` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
