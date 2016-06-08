<?php

error_reporting(E_ALL);
ini_set('display_errors', TRUE);
ini_set('display_startup_errors', TRUE);
date_default_timezone_set('Europe/London');

if (PHP_SAPI == 'cli')
    die('This example should only be run from a Web Browser');

require_once 'PHPExcel.php';


$objPHPExcel = new PHPExcel();

$objPHPExcel->getProperties()->setCreator("Coordinadores de Carreras")
        ->setLastModifiedBy("Coordinadores de Carreras")
        ->setTitle("Office 2007 XLSX Test Document")
        ->setSubject("Office 2007 XLSX Test Document")
        ->setDescription("Test document for Office 2007 XLSX, generated using PHP classes.")
        ->setKeywords("office 2007 openxml php")
        ->setCategory("Test result file");

$objPHPExcel->setActiveSheetIndex(0)
        ->setCellValue('A1', "Tecnológico Nacional de México Instituto Tecnológico de Veracruz")
        ->setCellValue('A2', "Materias mas solicitadas en para verano");
$styleArray = array(
    'font' => array(
        'bold' => true,
        'size'=>12,
    ),
    'alignment' => array(
        'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
    ),
    'borders' => array(
        'bottom' => array('style' => PHPExcel_Style_Border::BORDER_THIN),
        'top' => array('style' => PHPExcel_Style_Border::BORDER_THIN),
        'lef' => array('style' => PHPExcel_Style_Border::BORDER_THIN),
        'right' => array('style' => PHPExcel_Style_Border::BORDER_THIN)
    ),
);

$objPHPExcel->getActiveSheet()->getStyle('A1:G1')->applyFromArray($styleArray);
$objPHPExcel->getActiveSheet()->getColumnDimension('A')->setAutoSize(true);
$objPHPExcel->getActiveSheet()->getColumnDimension('B')->setAutoSize(true);
$objPHPExcel->getActiveSheet()->getRowDimension('1')->setRowHeight(20);
$objPHPExcel->getActiveSheet()->getStyle()->getFont()->setSize(20);
$objPHPExcel->getActiveSheet()->mergeCells('A1:G1');
$objPHPExcel->getActiveSheet()->getStyle()->getFont()->setSize(20);
$objPHPExcel->getActiveSheet()->getRowDimension('4')->setRowHeight(20);
$objPHPExcel->getActiveSheet()->getCell('A4')->setValue("Materias");
$objPHPExcel->getActiveSheet()->getCell('B4')->setValue("Solicitante");
$objPHPExcel->getActiveSheet()->getStyle('A1:G1')->getBorders()->getLeft()->setBorderStyle(PHPExcel_Style_Border::BORDER_THIN);
$objPHPExcel->getActiveSheet()->getStyle('A4:B4')->applyFromArray($styleArray);
$arrayMaterias = $_POST["id"];
$materias = preg_split("/[,]+/", $arrayMaterias);
$arrayAlumnos = $_POST["alumnos"];
$alumnos = preg_split("/[,]+/", $arrayAlumnos);
//
$index = 5;
$index2 = 0;
foreach ($materias as $materia) {
    $objPHPExcel->getActiveSheet()->getCell('A' . $index)->setValue($materia);
    $nombres = preg_split('/<br[^>]*>/i', $alumnos[$index2++]);
    foreach ($nombres as $nombre) {
        $objPHPExcel->getActiveSheet()->getCell('B' . $index++)->setValue($nombre);
    }
}
$objPHPExcel->getActiveSheet()->setTitle('Veranos');
$objPHPExcel->setActiveSheetIndex(0);
header('Content-Type: application/vnd.ms-excel');
header('Content-Disposition: attachment;filename="01simple.xls"');
header('Cache-Control: max-age=0');
header('Cache-Control: max-age=1');
header('Expires: Mon, 26 Jul 1997 05:00:00 GMT'); // Date in the past
header('Last-Modified: ' . gmdate('D, d M Y H:i:s') . ' GMT'); // always modified
header('Cache-Control: cache, must-revalidate'); // HTTP/1.1
header('Pragma: public'); // HTTP/1.0

$objWriter = PHPExcel_IOFactory::createWriter($objPHPExcel, 'Excel5');
$objWriter->save('php://output');
exit;
