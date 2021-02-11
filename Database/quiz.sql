-- phpMyAdmin SQL Dump
-- version 4.9.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jun 11, 2020 at 08:00 PM
-- Server version: 10.3.8-MariaDB
-- PHP Version: 7.3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quiz`
--

-- --------------------------------------------------------

--
-- Table structure for table `quiz_questions`
--

CREATE DATABASE quiz;
USE quiz;

CREATE TABLE `quiz_questions` (
  `id` int(11) NOT NULL,
  `question` varchar(111) NOT NULL,
  `option1` varchar(100) NOT NULL,
  `option2` varchar(100) NOT NULL,
  `option3` varchar(100) NOT NULL,
  `option4` varchar(100) NOT NULL,
  `correct_answer` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `quiz_questions`
--

INSERT INTO `quiz_questions` (`id`, `question`, `option1`, `option2`, `option3`, `option4`, `correct_answer`) VALUES
(37, 'hi', 'oo', 'ii', 'yy', 'ss', '1'),
(38, 'Who was the first president of India?', 'Pt. JawaharLal Nehru', 'Sardar Vallabhbhai Patel ', 'Dr. Rajendra Prasad', 'Mahatma Gandhi', '3');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `quiz_questions`
--
ALTER TABLE `quiz_questions`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `question` (`question`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `quiz_questions`
--
ALTER TABLE `quiz_questions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
