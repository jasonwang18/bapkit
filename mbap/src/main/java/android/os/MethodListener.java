package android.os;

public abstract interface MethodListener
{
    public abstract void onInvokeV(String paramString, boolean paramBoolean, Object paramObject);

    public abstract int onInvokeI(String paramString, boolean paramBoolean, Object paramObject);

    public abstract long onInvokeL(String paramString, boolean paramBoolean, Object paramObject);

    public abstract float onInvokeF(String paramString, boolean paramBoolean, Object paramObject);

    public abstract double onInvokeD(String paramString, boolean paramBoolean, Object paramObject);

    public abstract Object onInvokeA(String paramString, boolean paramBoolean, Object paramObject);
}