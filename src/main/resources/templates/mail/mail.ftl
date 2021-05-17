<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body style="background: linear-gradient(#fafae3,#d7d7c8);">
<table align="center" style="padding-top: 50px; background-color: #fafdff;max-width: 600px; width: 100%; height: 500px; border-radius: 1rem;
    border-color: white; margin-top: 30px; margin-bottom: 50px;">
    <tbody style="width: 100%;">
    <tr style="">
        <td style="alignment: center;">

            <table style="width: 100%;">
                <tbody>
                <tr>
                    <td style="width: 25%;"></td>
                    <td style="width: 50%;">
                        <!--   <img src="/static/images/logo.png" style="width: 100%;height: auto; margin-left: auto; margin-right: auto;">-->
                       <img src="cid:attachment.png" style="width: 100%;height: auto; margin-left: auto; margin-right: auto;">
                    </td>
                    <td style="width: 25%;"></td>
                </tr>
                </tbody>
            </table>
        </td>
    </tr>

    <tr style="align-items: flex-start; text-align: center;">
        <td>
            Привіт ${name}!
        </td>
    </tr>
    <tr>
        <td>
            <table>
                <tbody>
                <tr style="width: 100%;">
                    <td style="width: 10%;"></td>
                    <td style="width: 80%;">
                        <p>Здається ви у нас реєструвались.</p>
                        <p>Для підтвердження вашої електронної скриньки будь-ласка скористуйтесь цим
                            посиланням:</p>
                    </td>
                    <td style="width: 10%;"></td>
                </tr>
                </tbody>
            </table>
        </td>
    </tr>
    <tr style="text-align: center;">
        <td>
            <a style="width: 200px;    height: 50px;    background: #1e85c9;
                            padding: 10px;    text-align: center;    border-radius: 5px;    color: white;
                            font-weight: bold;    line-height: 25px; text-decoration: none; text-transform: uppercase;"
               href="${link!'#'}">
                Підтвердити регістрацію
            </a>
        </td>
    </tr>
    <tr style="text-align: center;">
        <td>
        </td>
    </tr>
    </tbody>
</table>
</body>
</html>