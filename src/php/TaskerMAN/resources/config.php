<?php
/**
 * Created by PhpStorm.
 * User: Oliver
 * Date: 16/11/2015
 * Time: 00:23
 */

define("SITENAME","TaskerMAN");
define("SITEVER","Alpha 1.0");

$config = array (
    "database" => array (
        "hostname"  => "",
        "dbname"    => "",
        "username"  => "",
        "password"  => "",
    ),
    "paths" => array (
        "include"   => $_SERVER["DOCUMENT_ROOT"] . "/include",
        "resources" => $_SERVER["DOCUMENT_ROOT"] . "/resources",
        "css"       => $_SERVER["DOCUMENT_ROOT"] . "/css",
        "img"       => $_SERVER["DOCUMENT_ROOT"] . "/img",
        "js"        => $_SERVER["DOCUMENT_ROOT"] . "/js",
    )
);

date_default_timezone_set("Europe/London");
