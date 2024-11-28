-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 20, 2023 at 06:01 AM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 7.3.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

-- Database: `theme_art_tools`

-- --------------------------------------------------------

-- Table structure for table `tools`

CREATE TABLE `tools` (
  `ToolID` varchar(6) NOT NULL,
  `ToolName` varchar(50) NOT NULL,
  `PowerRequirement` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table `tools`

INSERT INTO `tools` (`ToolID`, `ToolName`, `PowerRequirement`) VALUES
('001', 'Paint Sprayer', 500),
('002', 'Airbrush Kit', 100),
('003', '3D Printer', 1500),
('004', 'Sculpting Drill', 800),
('005', 'Cutting Plotter', 1000),
('006', 'Heat Gun', 1500),
('007', 'Lightbox', 75);

-- --------------------------------------------------------

-- Table structure for table `technicians`

CREATE TABLE `technicians` (
  `TechnicianID` varchar(13) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `ToolID` varchar(5) NOT NULL,
  `Experience` float NOT NULL,
  `Certified` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table `technicians`

INSERT INTO `technicians` (`TechnicianID`, `Name`, `ToolID`, `Experience`, `Certified`) VALUES
('311113104025', 'Alex', '002', 10.2, 'YES'),
('311113104039', 'Maria', '004', 5.7, 'NO');

-- Indexes for dumped tables

-- Indexes for table `tools`
ALTER TABLE `tools`
  ADD PRIMARY KEY (`ToolID`);

-- Indexes for table `technicians`
ALTER TABLE `technicians`
  ADD PRIMARY KEY (`TechnicianID`);

COMMIT;
