let hotel_list = [{
    Name: "",
    City: "",
    District: "",
    Date: "",
    Time: "",
    Price: "",
    Type: "",
    Id: ""
}];

let id = 0;

function onClickAddHotel(){
    document.getElementById("fade").style.display = "block"
    document.getElementById("form").style.display = "block"
}

function onClickConfirm() {
    let name = document.getElementById("name").value
    let radio = document.getElementsByName("city")
    let district = document.getElementById("district").value
    let date = document.getElementById("date").value
    let time = document.getElementById("time").value
    let price = document.getElementById("price").value
    let type = document.getElementById("type").value
    let body = document.getElementById('tbody');
    let city;
    for (let i = 0; i < radio.length; i++) {
        if (radio[i].checked) {
            city = radio[i].value
            break
        }
    }

    if (!body) {
        alert("Body of Table not Exist!");
        return;
    }

    if (isValid(name, city, district, date, time, price, type)){
        let new_hotel = {
            Name: name,
            City: city,
            District: district,
            Date: date,
            Time: time,
            Price: price,
            Type: type,
            Id: id.toString()
        }
        hotel_list.push(new_hotel)


        body.style.display = "";//显示之后再隐藏
        let rowCount = body.rows.length;
        let cellCount = body.rows[0].cells.length;
        let new_row = body.insertRow(rowCount++);

        new_row.insertCell(0).innerHTML = name;
        new_row.insertCell(1).innerHTML = city;
        new_row.insertCell(2).innerHTML = district;
        new_row.insertCell(3).innerHTML = date;
        new_row.insertCell(4).innerHTML = time;
        new_row.insertCell(5).innerHTML = price;
        new_row.insertCell(6).innerHTML = type;
        new_row.insertCell(7).innerHTML = body.rows[0].cells[cellCount-2].innerHTML;
        new_row.insertCell(8).innerHTML = id.toString();
        id++;
        altColor(body);
        body.rows[0].style.display = "none";//隐藏第一行
        new_row.cells[8].style.display = "none";//隐藏id
        document.getElementById("fade").style.display = "none"
        document.getElementById("form").style.display = "none"
   }

}

function onClickClose(){
    document.getElementById("fade").style.display = "none"
    document.getElementById("form").style.display = "none"
}

function isValid(name, city, district, date, time, price, type){
    // return true
    if (name.length === 0) {
        alert("Please input Hotel Name!")
        return false
    }else if (city.length === 0){
        alert("Please input City!")
        return false
    }else if (district.length === 0){
        alert("Please input District!")
        return false
    }else if (date.length === 0){
        alert("Please input Date!")
        return false
    }else if (time.length === 0){
        alert("Please input check-in time!")
        return false
    }else if (price.length === 0){
        alert("Please input Price!")
        return false
    }else if (type.length === 0){
        alert("Please input Room Type!")
        return false
    }

    let nameRegex = new RegExp(/^[a-zA-Z]+$/);
    if (!nameRegex.test(name)){
        alert("Invalid Hotel Name!\nHotel Name Should only contain English Letters!")
        return false
    }

    let current = Current();
    if (current >= date) {
        alert("Invalid Date!")
        return false
    }

    if (!(price>0 && price<100000)){
        alert("price should between 0 and 99999")
        return false
    }

    for (let i=0;i<hotel_list.length;i++){
        if (hotel_list[i].Name===name && hotel_list[i].City===city && hotel_list[i].District===district && hotel_list[i].Type===type){
            alert("Hotel Room Exist!")
            return false
        }else if (hotel_list[i].Name===name && hotel_list[i].City===city && hotel_list[i].District===district && hotel_list[i].Price===price){
            alert("Price should not be same!")
            return false
        }
    }

    return true
}

function Current(){//获取现在的日期
    let current = new Date();
    let curYear = current.getFullYear();
    let curMonth = current.getMonth()+1;
    let curDate = current.getDate();
    if (curMonth<10) curMonth = "0" + curMonth;
    if (curDate<10) curDate = "0" + curDate;
    return curYear+"-"+curMonth+"-"+curDate;
}

function districtChange(value){//根据city改变区选项
    if (value === "GUANG ZHOU"){
        for (let i=0;i<document.getElementById("district").options.length;i++){
            if (i<11) document.getElementById("district").options[i].style.display = "block";
            else document.getElementById("district").options[i].style.display = "none";
        }
        document.getElementById("district").options[0].selected = true;
    } else if (value === "SHEN ZHEN"){
        for (let i=0;i<document.getElementById("district").options.length;i++){
            if (i>=11) document.getElementById("district").options[i].style.display = "block";
            else document.getElementById("district").options[i].style.display = "none";
        }
        document.getElementById("district").options[11].selected = true;
    }
}

function deleteRow(object){
    let tr = object.parentNode.parentNode;//按钮父亲的父亲是tr 找到表格
    let tbody = tr.parentNode;//删除行
    tbody.removeChild(tr);
    //在hotel list中删除对应的hotel
    let cellCount = tr.cells.length;
    let this_id = tr.cells[cellCount-1].innerText;
    for (let i=0;i<hotel_list.length;i++){
        if (this_id === hotel_list[i].Id) {
            hotel_list.splice(i,1);
            break;
        }
    }
    altColor(tbody);
}

function altColor(body){
    let rows = body.rows;
    for (let i=0;i<rows.length;i++){
        if (i%2 === 0) rows[i].className = "evenColor";
        else rows[i].className = "oddColor";
    }
}

