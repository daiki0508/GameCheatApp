<?php
    // jsonを取得
    $json = file_get_contents("php://input");
    // JSON文字列をobjectに変換
    $contents = json_decode($json, true);

    error_log(var_export($contents, true), 3, './result_log.txt')
?>