Copy & paste this sql code to a sqlite3 terminal.<br/>
The terminal can be your pc with a local [installation of sqlite3](https://sqlite.org/download.html).<br/>
Or you can `adb shell` to your Android emulator, and look for your app under `/data/data/....your.pname.here../databases` and then type `sqlite3 your.db`.


<pre>
sqlite3 

.headers on
.mode column

create table favcol (_id integer primary key not null, username text, fav_color text) ;

select * from favcol;

insert into favcol (_id,username,fav_color) values (1,'amir','blue'),(2,'liat','white'),(2,'david','blue');
<b>
    Error: UNIQUE constraint failed: favcol._id
</b>
insert into favcol (_id,username,fav_color) values (1,'amir','blue'),(2,'liat','white'),(3,'david','blue');

select * from favcol;

<b>
    _id          username    fav_color 
    ----------  ----------  ----------
    1           amir        blue      
    2           liat        white     
    3           david       blue      
</b>

insert into favcol (username,fav_color) values ('yaron','green');

<b>
    _id         username    fav_color 
    ----------  ----------  ----------
    1           amir        blue      
    2           liat        white     
    3           david       blue      
    4           yaron       green
         
    // notice the autoincrement id
</b>

select * from favcol where fav_color='blue';

<b>
    _id          username    fav_color 
    ----------  ----------  ----------
    1           amir        blue      
    3           david       blue      
</b>


select count(*) from favcol where fav_color='blue';
<b>
    count(*)  
    ----------
    2         
</b>

select * from favcol where username='Liat';
<b>
    // (empty result)
</b>

select * from favcol where username like 'L%';
<b>
    _id          username    fav_color 
    ----------  ----------  ----------
    2           liat        white     

    // (notice: not case sensitive)

</b>

create table colors ('clr' text, 'example' text) ;
insert into colors (clr,example) values ('red','blood'),('white','wall'),('yellow','sun'),('blue','sky'),('white','paper');
select * from colors;
<b>
    clr         example   
    ----------  ----------
    red         bloud     
    white       wall      
    yellow      sun       
    blue        sky       
    white       paper     

</b>
select * from favcol s join colors c on s.fav_color=c.clr;
<b>
    _id          username    fav_color   clr         example   
    ----------  ----------  ----------  ----------  ----------
    1           amir        blue        blue        sky       
    2           liat        white       white       paper     
    2           liat        white       white       wall      
    3           david       blue        blue        sky       
</b>

select * from favcol s left join colors c on s.fav_color=c.clr;
<b>
    _id         username    fav_color   clr         example   
    ----------  ----------  ----------  ----------  ----------
    1           amir        blue        blue        sky       
    2           liat        white       white       paper     
    2           liat        white       white       wall      
    3           david       blue        blue        sky       
    4           yaron       green                             

    `left join` also returns rows from s that has no match.
    `right join` is not supported in sqlite. (Some other DB's do, such as MySql).
</b>


select username,clr,example from favcol s join colors c on s.fav_color=c.clr;
<b>
    username    clr         example   
    ----------  ----------  ----------
    amir        blue        sky       
    liat        white       paper     
    liat        white       wall      
    david       blue        sky       

</b>

.tables 
<b>
    colors  favcol
</b>
drop table favcol;
drop table colors;

</pre>