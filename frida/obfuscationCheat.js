Java.perform(function(){
    var fightActivity = Java.use("com.websarva.wings.android.gamecheatapp.ui.FightActivity");
    fightActivity.v.implementation = function(v){
        console.log('[*] v() Hook!');
        var a = Java.use("h3.c$a");
        a.b.implementation = function(int){
            console.log('[*] b() Hook!');
            if(int == 100){
                // userの攻撃力を改ざん
                console.log("[*] Changed the damage that User deals to Fenrir.");
                return 200;
            }else{
                return this.b(int);
            }
        };
        this.v(v);
    }
    fightActivity.onTouchEvent.implementation = function(e){
        console.log('[*] onTouchEvent() Hook!');
        var a = Java.use("h3.c$a");
        a.b.implementation = function(int){
            console.log('[*] b() Hook!');
            if(int == 150){
                // フェンリルの攻撃力を改ざん
                console.log('[*] Changed the damage that Fenrir deals to User.')
                return 0;
            }else{
                return this.b(int);
            }
        }
        this.onTouchEvent(e);
        return true;
    }

    fightActivity.onCreate.implementation = function(b){
        console.log('[*] onCreate() Hook!');
        var a = Java.use("h3.c$a");
        a.b.implementation = function(int){
            console.log('[*] b() Hook!');
            if(int == 251){
                // UserのdefaultHPを改ざん
                console.log('[*] Changed User\'s default HP.')
                return 1000;
            }else if(int == 501){
                // フェンリルのdefaultHPを改ざん
                console.log('[*] Changed Fenrir\'s default HP.')
                return -300;
            }else{
                return this.b(int);
            }
        }
        
        this.onCreate(b);
    }
})