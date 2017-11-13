var notify={
    success:function(s){
        layer.open({
            content: '' +
            '<div>' +
            '   <i class="layui-icon" style="color: #00a854">&#xe605;</i> ' +s+
            '</div>',
            title: false,
            time:6000,
            closeBtn: 0,
            btn:[],
            offset:'20px',
            shade: 0,
            anim:0
        });
    },
    error:function(s){
        layer.open({
            content: '' +
            '<div>' +
            '   <i class="layui-icon" style="color: #f04134">&#x1006;</i> ' +s+
            '</div>',
            title: false,
            time:6000,
            closeBtn: 0,
            btn:[],
            offset:'20px',
            shade: 0,
            anim:0
        });
    },
    warn:function(s){
        layer.open({
            content: '' +
            '<div>' +
            '' +s+
            '</div>',
            title: false,
            time:6000,
            closeBtn: 0,
            btn:[],
            offset:'20px',
            shade: 0,
            anim:0
        });
    },
    info:function (s) {
        layer.open({
            content: '' +
            '<div>' +
            '   <i class="layui-icon" style="color: #40AFFE">&#xe60b;</i> ' +s+
            '</div>',
            title: false,
            time:6000,
            closeBtn: 0,
            btn:[],
            offset:'20px',
            shade: 0,
            anim:0
        });
    }

}