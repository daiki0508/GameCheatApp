Java.perform(function(){
    var fightActivity = Java.use("com.websarva.wings.android.gamecheatapp.ui.FightActivity");
    fightActivity.userAttack.implementation = function(v){
        console.log('[*] userAttack() Hook!');
        var random = Java.use("kotlin.random.Random$Default");
        random.nextInt.overload('int').implementation = function(int){
            console.log("[*] nextInt() Hook!");
            if(int == 100){
                // userの攻撃力を改ざん
                console.log("[*] Changed the damage that User deals to Fenrir.");
                return 200;
            }else{
                return this.nextInt(int);
            }
        }
        this.userAttack(v);
    }
    fightActivity.fenrirAttack.implementation = function(v){
        console.log('[*] fenrirAttack() Hook!');
        var random = Java.use("kotlin.random.Random$Default");
        random.nextInt.overload('int').implementation = function(int){
            console.log("[*] nextInt() Hook!");
            if(int == 150){
                // フェンリルの攻撃力を改ざん
                console.log('[*] Changed the damage that Fenrir deals to User.')
                return 0;
            }else{
                return this.nextInt(int);
            }
        }
        this.fenrirAttack(v);
    }

    fightActivity.onCreate.implementation = function(b){
        console.log('[*] onCreate() Hook!');
        var random = Java.use("kotlin.random.Random$Default");
        random.nextInt.overload('int').implementation = function(int){
            console.log("[*] nextInt() Hook!");
            if(int == 251){
                // UserのdefaultHPを改ざん
                console.log('[*] Changed User\'s default HP.')
                return 1000;
            }else if(int == 501){
                // フェンリルのdefaultHPを改ざん
                console.log('[*] Changed Fenrir\'s default HP.')
                return 100;
            }else{
                return this.nextInt(int);
            }
        }
        this.onCreate(b);
    }
})