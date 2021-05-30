<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script>
        $(document).ready(function() {
            show();
            setInterval ('show()',1000);
        });

        function show() {
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/job4j_todo_war_exploded/item',
                dataType: 'json'
            }).done(function (data) {
                let tbody = document.querySelector('tbody');
                let tbtr = document.querySelectorAll('tbody tr');
                $.each(tbtr, function (i, e) {
                    e.remove();
                });
                let showAll = document.querySelector('#showAll');
                $.each(data, function (i, e) {
                    if (!showAll.checked && e.done) {
                        return;
                    }
                    let tr = document.createElement('tr');
                    tr.setAttribute('id', e.id);
                    let num = document.createElement('td');
                    if (e.done === false) {
                        num.innerHTML = '<form action="item" method="post">' +
                            '<input type="hidden" class="form-control" id="desc" name="desc" value="' + e.description + '">' +
                            '<input type="hidden" class="form-control" id="id" name="id" value="' + e.id + '">' +
                            '<button style="background-color: Red" title="Выполнить!"><i class="fa fa-close"></i></button></form>';
                    } else {
                        num.innerHTML = '<form action="delete" method="post">' +
                            '<input type="hidden" class="form-control" id="id" name="id" value="' + e.id + '">' +
                            '<button style="background-color: Lime" title="Удалить!"><i class="fa fa-check" ></i></button></form>';
                    }
                    let desc = document.createElement('td');
                    desc.innerText = e.description;
                    let create = document.createElement('td');
                    create.innerText = e.created;
                    tr.append(num);
                    tr.append(desc);
                    tr.append(create);
                    tbody.append(tr);
                });
            }).fail(function (err) {
                alert(err);
            });
        }
    </script>
    <title>ToDo</title>
</head>
<body>
<div class="container">
    <div class="row pt-3">
        <h4>
            ToDo
        </h4>
    </div>
    <div class="row">
        <form action="/job4j_todo_war_exploded/item" method="post">
            <div class="form-group">
                <label for="desc">Описание задания</label>
                <input type="text" required class="form-control" id="desc" name="desc" placeholder="Описание задания..." title="Описание задания..." >
            </div>
            <button type="submit" class="btn btn-success">Добавить новое задание!</button>
        </form>
    </div>
    <div class="row pt-3">
        <h6>Список добавленных заданий</h6>
    </div>
    <div class="row">
        <div class="form-check pb-2">
            <input type="checkbox" class="form-check-input" id="showAll" name="showAll">
            <label class="form-check-label" for="showAll">Показать все выполненые  </label>
        </div>
    </div>
    <div class="row">
        <div>
            <i class="fa fa-times mr-3"> Не выполнено!</i>
            <i class="fa fa-check mr-3"> Выполнено!</i>
        </div>
    </div>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th width="5%" style="text-align: center">Статус</th>
            <th width="60%">Описание</th>
            <th width="35%">Дата создания</th>
        </tr>
        </thead>
        <tbody id="table">
        </tbody>
    </table>
</div>
</body>
</html>
