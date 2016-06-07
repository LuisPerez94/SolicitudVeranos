<?php

error_reporting(E_ALL);
ini_set('display_errors', TRUE);
ini_set('display_startup_errors', TRUE);
date_default_timezone_set('Europe/London');

if (PHP_SAPI == 'cli')
    die('This example should only be run from a Web Browser');

/** Include PHPExcel */
require_once 'PHPExcel.php';


// Create new PHPExcel object
$objPHPExcel = new PHPExcel();

// Set document properties
$objPHPExcel->getProperties()->setCreator("Coordinadores de Carreras")
        ->setLastModifiedBy("Coordinadores de Carreras")
        ->setTitle("Office 2007 XLSX Test Document")
        ->setSubject("Office 2007 XLSX Test Document")
        ->setDescription("Test document for Office 2007 XLSX, generated using PHP classes.")
        ->setKeywords("office 2007 openxml php")
        ->setCategory("Test result file");


// Add some data
$objPHPExcel->setActiveSheetIndex(0)
        ->setCellValue('A1', "Tecnológico Nacional de México Instituto Tecnológico de Veracruz")
        ->setCellValue('A2', "Materias mas solicitadas en para verano");
$objPHPExcel->getActiveSheet()->getColumnDimension('A')->setAutoSize(true);
$objPHPExcel->getActiveSheet()->getRowDimension('1')->setRowHeight(20);
$objPHPExcel->getActiveSheet()->getStyle()->getFont()->setSize(20);
$objPHPExcel->getActiveSheet()->mergeCells('A1:G1');
// Miscellaneous glyphs, UTF-8
$objPHPExcel->getActiveSheet()->getCell('A4')->setValue("Materias");
$objPHPExcel->getActiveSheet()->getCell('C4')->setValue("Solicitante");

$arrayMaterias = $_POST["id"];
$materias =  preg_split("/[,]+/", $arrayMaterias);
$arrayAlumnos = $_POST["alumnos"];
$alumnos = preg_split("/[,]+/", $arrayAlumnos);
//
$index=5;
$index2=0;
foreach ($materias as $materia){
    $objPHPExcel->getActiveSheet()->getCell('A'.$index)->setValue($materia);
    $nombres=  preg_split('/<br[^>]*>/i', $alumnos[$index2++]);
        foreach($nombres as $nombre){
            $objPHPExcel->getActiveSheet()->getCell('C'.$index++)->setValue($nombre);
        }

}



$objPHPExcel->getActiveSheet()->getCell('A8')->setValue("hello\nworld");

// Rename worksheet
$objPHPExcel->getActiveSheet()->setTitle('Veranos');


// Set active sheet index to the first sheet, so Excel opens this as the first sheet
$objPHPExcel->setActiveSheetIndex(0);


// Redirect output to a client’s web browser (Excel5)
header('Content-Type: application/vnd.ms-excel');
header('Content-Disposition: attachment;filename="01simple.xls"');
header('Cache-Control: max-age=0');
// If you're serving to IE 9, then the following may be needed
header('Cache-Control: max-age=1');

// If you're serving to IE over SSL, then the following may be needed
header('Expires: Mon, 26 Jul 1997 05:00:00 GMT'); // Date in the past
header('Last-Modified: ' . gmdate('D, d M Y H:i:s') . ' GMT'); // always modified
header('Cache-Control: cache, must-revalidate'); // HTTP/1.1
header('Pragma: public'); // HTTP/1.0

$objWriter = PHPExcel_IOFactory::createWriter($objPHPExcel, 'Excel5');
$objWriter->save('php://output');
exit;
