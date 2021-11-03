<?php

#exit ;
date_default_timezone_set('Asia/Omsk');

#require_once '../../conf.php';

#########
#mysql_set_charset('utf8', $link1);

$key = 'reJ59jAEK1n04VaMirIclN';
$phone = isset($_REQUEST["PHONE"]) ? $_REQUEST["PHONE"] : "";


//print("0");
//exit;

$phone = "79503367777";

$key_get = isset($_REQUEST["KEY"]) ? $_REQUEST["KEY"] : "";

$strError = "phone = " . $phone . " key_get = " . $key_get;
//system('echo "error '.$strError.'" >> /tmp/clever.txt');
//system('echo "md5 '.md5($phone.$key).'" >> /tmp/clever.txt');

/*
$mykey = md5($card.$key);
print("$mykey<br>
$key_get<br>
");
exit;
*/

//if(md5($phone.$key)==$key_get){
if (true) {

//###########3

    class Sms
    {

        public $params = array(
            'timestamp' => '',
            'login' => 'TOP_Line',
            'phone' => '',
            'sender' => 'Clever_Card',
            'return' => 'xml',
            'text' => ''
        );
        private $aParams = array(
            'timestamp' => '',
            'login' => 'TOP_Line',
            'phone' => '',
            'signature' => '',
            'sender' => 'Clever_Card',
            'return' => 'xml',
            'text' => ''
        );
        public $login = "TOP_Line";
        public $password = "dsTr432x";
        public $url = "http://lk.pigeon-telecom.ru/sendsmsjson.php";

        public $nTypeError;
        public $smsCode = 1000;
//public $host = '80.89.154.94:8080';
//public $hostNew = '192.168.19.43/clever/sel.php';
        public $host = 'lk.pigeon-telecom.ru/external/get/send.php';
        protected $keySms = 'a59578a3d1fbf7aac2075c39b88745c314437333';

        public function getCode($phoneNumber)
        {

            $param = array(
                'security' => array('login' => '$login', 'password' => 'password'),
                'type' => 'sms',
                'message' => array(
                    array(
                        'type' => 'sms',
                        'sender' => 'Clever_Card',
                        'text' => "Код подтверждения " . rand(1001, 9999) . ".",
                        'name_delivery' => 'Рассылка 1',
                        'translite' => '1',
                        'abonent' => array(
                            //array('phone' => '79001234567', 'number_sms' => '1', 'client_id_sms' => '100', 'time_send' => '2016-11-09 12:40', 'validity_period' => '2016-11-09 13:30'),
                            //array('phone' => '79001234568', 'number_sms' => '2', 'client_id_sms' => '101')
                            array('phone' => '$phoneNumber')
                        )
                    )
                )
            );


//            $this->params['phone'] = $phoneNumber;
//            $this->aParams['phone'] = $this->params['phone'];
//            //$this->params['timestamp'] = md5($phoneNumber.$this->key);
//
//            $this->params['timestamp'] = $this->getTimestamp();
//            $this->aParams['timestamp'] = $this->params['timestamp'];
//            $this->smsCode = rand(1001, 9999);
//            $this->params['text'] = "Код подтверждения " . $this->smsCode . ".";
//            $this->aParams['text'] = $this->params['text'];
//            ksort($this->params);
//            reset($this->params);
//
//            $str = implode($this->params) . $this->keySms;
//
//            $signature = md5($str);
//            $this->aParams['signature'] = $signature;
//

            $sResponse = $this->sendRequestSms($param);
//$aResponse = $this->processingResponse($sResponse);
            return $sResponse;
        }

        private function sendRequestSms($param)
        {
            $param_json = json_encode($param, true);
            $href = $this->url;
            $ch = curl_init();
            //$url = 'http://'.$this->host.'?'.http_build_query($this->aParams);
            curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json', 'charset=utf-8', 'Expect:'));
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($ch, CURLOPT_POST, true);
            curl_setopt($ch, CURLOPT_POSTFIELDS, $param_json);
            curl_setopt($ch, CURLOPT_TIMEOUT, 600);
            curl_setopt($ch, CURLOPT_URL, $href);
            curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);
            curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0);
            $res = curl_exec($ch);
            $result = json_decode($res, true);
            curl_close($ch);
            print_r($result);


            $sResponse = $res;
            if (curl_error($ch)) {
                return false;
            }
            curl_close($ch);
            return trim($sResponse);
        }

        private function getTimestamp()
        {
            $ch = curl_init();
            $url = 'http://lk.pigeon-telecom.ru/external/get/timestamp.php';
            curl_setopt($ch, CURLOPT_URL, $url);
            curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
            curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, false);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
            $sResponse = curl_exec($ch);
            if (curl_error($ch)) {
                return false;
            }
            curl_close($ch);
            return trim($sResponse);
        }
    }

    $sms = new Sms();
    $phone = "79503367777";
    $error = $sms->getCode($phone);
    //$error = '[{"79503367777":{"error":"0","id_sms":"4507707081058633490001","cost":"1.4","count_sms":1}}]';
    /*
    $error = '<?xml version="1.0" encoding="utf-8"?>
            <response>
                <phones>
                    <phone phone="79503367777" error="0" id_sms="4507706791731224300001" cost="1.4" count_sms="1" />
                </phones>
            </response>';
*/
//echo "error - $error\n";

    exit(0);
    $simple = $error;
    $p = xml_parser_create();
    xml_parse_into_struct($p, $simple, $vals, $tags);
    xml_parser_free($p);

    $hint = -1;
    //var_dump($error);
    //echo "vals:\n";
    //var_dump($vals);
    if (isset($vals[1]['attributes']['ERROR'])) {
        if ($vals[1]['attributes']['ERROR'] == 0) {
            echo "sms: ok\n";
            $codeBase = codeToBase($sms);
            echo "sms to db: ok\n";
            if ($codeBase == 0) {
                $hint = 0;
            } else $hint = -4;
        } else {
            $hint = -3;
        }

    } else {
        $hint = -2;
    }

//###########33


    $response = $hint;
    print("$response\n");
    exit;

} else {
    print("-5");

    exit;

}

//require_once '../online/conf.php';

function codeToBase(Sms $sms)
{
    $server = "localhost";
    //имя пользователя для соединения с БД
    $username = "onlineuser";
    //пароль для соединения с БД
    $password = "onlinepassword";
    //имя БД
    $db = "online";

    $link1 = mysql_connect($server, $username, $password) or die("Нет доступа к базе:" . mysql_error());
    mysql_select_db($db, $link1);

    $sql = "insert into my_phone_codes
        (phone, sms_code, sms_checked, sms_date)
        values ('" . $sms->params['phone'] . "','" . $sms->smsCode . "', '0', '" . $sms->params['timestamp'] . "')
    ";
    //echo $sql."\n";
    $res = mysql_query($sql, $link1) or die("Нет доступа к базе:" . mysql_error());
    $sql = "select * from my_phone_codes
        where phone = '" . $sms->params['phone'] . "' and sms_code = '" . $sms->smsCode . "' and sms_checked=0 and sms_date=" . $sms->params['timestamp'] . "
    ";
    $res = mysql_query($sql, $link1) or die("Нет доступа к базе:" . mysql_error());
    if ($res)
        $rows = mysql_num_rows($res);
    else $rows = 0;
    if ($rows == 1) {
        return 0;
    } else return -1;
}

#list($day, $month, $year) = split('[/.-]', $from);
//$time_from = mktime(0,0,0,$month,$day,$year);
//$time_from = mktime(0,0,0,$month,$day,$year);
#$from = "$year-$month-$day";


?>