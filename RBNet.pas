
��¼��
   CLIENT_NAME = 'LHCLIENT';
    DOG_KEY = 'RBGAMES';
    PlayerId := FPlayerId;

�˿ں�	
	32765
��¼����ʽ
    PClientInfo = ^TClientInfo;
    TClientInfo = packed record
        Name: string[32];
        Pwd: string[32];
        Level: Integer;
        IP: string[19];
        GameId: Integer;
        PlayerId: Integer;
        LifeTimeCount: Integer;
        LoginTime: TDateTime;
        LoginSuccess: Boolean;
        LoginType: Integer;
        OtherData: TClientOtherData;
    end;
��������:    
					586686 ��¼��
	CMD_DISCONNECT = 586687;
    CMD_LIFE = 586688;
    LIFE_TIME = 10 * 1000;

unit RBNet;

interface

uses mmGame, mmCard;

const
{$IFDEF LongHu}
    BET_COUNT = 3;                      //Ѻ����Ŀ����
    BOUT_COUNT = 66;
    CARDS_COUNT = 2;                    //������
{$ELSE}
    BET_COUNT = 5;                      //Ѻ����Ŀ����
    BOUT_COUNT = 100;
    CARDS_COUNT = 1;                    //������
{$ENDIF}
    //����Э������ͷ
    CMD_GAMEDATA = 5001;
    CMD_OPTIONDATA = 5002;
    CMD_PLAYERDATA = 5003;
    CMD_GAMETIMEDATA = 5004;
    CMD_GAMECARDDATA = 5005;
    CMD_PLAYERBETDATE = 5006;
    CMD_UPDOWNSCOREDATA = 5007;
    CMD_PLAYERCANCELBETDATA = 5008;
    CMD_GAMELDDATA = 5009;
    CMD_INOUTCOINDATA = 5010;
    CMD_CLEARBOARD = 5011;
    CMD_STOPFLASH = 5012;
    CMD_BANKERTIME = 5013;
    CMD_BANKERDATA = 5014;
    CMD_CLEARDATA = 5015;
    CMD_CLOSE = 5016;
    CMD_LUCKYCARDS = 5017;
    CMD_BAOJI = 5018;
    CMD_ROUNDTIME = 5019;
    CMD_COIN = 5020;
    CMD_UPDOWNOK = 5021;
    CMD_DISCERNERROR = 5022;
    CMD_DOGERROR = 5023;
    CMD_CARDINDEX = 5024;
    CMD_CUTINDEX = 5025;
    CMD_ALLBET = 5026;
type

    TGameData = packed record
        GameBout: Integer;
        GameRound: Integer;
        GameState: TGameState;
        GameNowTry: Byte;
    end;

    TOptionData = packed record
        GameMaxBet: Integer;
        GameMaxOne: Integer;
        GameMinBet: Integer;
        GameMinBetStep: Integer;
        GameMaxBetStep: Integer;
        GameBankerTime: Byte;��ׯ����ʱ
        GameBankerCount: Byte;��ׯ����
        GameBankerPayOutRate: Byte;
        GamePlayerPayOutRate: Byte;
        GameMinBankerScore: Integer;
        GameOutCoinType: Boolean;
        GameCoinStep: Integer;
        GameRoundTime: Integer;
        GameCutCount: Byte;���ƴ���
{$IFDEF DanTiao}
        GameMaxKing: Integer;
        GameLdShowType: Byte;
{$ENDIF}
    end;

    TItemBetMoney = array[0..BET_COUNT - 1] of Integer;
    TPlayerData = packed record
        TolMoney: Integer;
        ItemBet: TItemBetMoney;
        TolWin: Integer;
        BoutWithDraw: Integer;������
    end;

    TGameTimeData = packed record
        GameTime: Integer;
        GameOutMax: array[0..BET_COUNT - 1] of Boolean;
    end;

    TBankerTimeData = packed record
        BankerTime: Byte;
    end;

    TUpDownOk = packed record
    end;

    TClearBoradOk = packed record
        PlayerId: Byte;
    end;

    TCardDiscernError = packed record
    end;

    TCardIndex = packed record
        CardIndex: Byte;
    end;

    TCutIndex = packed record
        CutIndex: Byte;��ǰ�еڼ�����
    end;

    TDogError = packed record
    end;

    TRoundTimeData = packed record
        RoundTime: Integer;
    end;

    TBankerData = packed record
        PlayerId: Byte;
        BankerID: Byte;
        BankerTimes: Byte;��ǰ�ڼ�����ׯ
    end;

    TStopFlash = packed record
    end;

    TGameCard = packed record
        Color: Byte;
        Point: Byte;
    end;

    TCoinData = packed record
        PlayerId: Byte;
        InNum: Integer;
        OutNum: Integer;
    end;

    TGameLuckyCard = packed record
        Card: array[0..CARDS_COUNT - 1] of TGameCard;
    end;

    TWinItemCount = array[0..BET_COUNT - 1] of Integer;
    TGameCardData = packed record
        Card: array[0..CARDS_COUNT - 1] of TGameCard;
        WinItem: Byte;
{$IFDEF DanTiao}
        WinItemCout: TWinItemCount;
{$ENDIF}
    end;

    TPlayerAllBet = packed record
        BetItem: TWinItemCount;
    end;
    TPlayerBetData = packed record
        PlayerId: Byte;
        BetItem: Byte;
        BetMoney: Integer;
    end;

    TUpDownScoreData = packed record
        PlayerId: Byte;
        TolMoney: Integer;
        UpDownMoney: Integer;
    end;

    TPlayerCancelData = packed record
        PlayerId: Byte;
    end;

    TGameLdData = packed record
        Bout: Integer;
        Records: array[0..BOUT_COUNT - 1] of Integer;
{$IFDEF DanTiao}
        WinItemCout: TWinItemCount;
{$ENDIF}
    end;

    TInOutCoinData = packed record
        TolMoney: Integer;
        PlayerId: Byte;
        InOutCoin: Integer;
    end;
implementation

end.

 
