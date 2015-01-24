package net.dimensionshiftinfectus.mod.tileentity;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityGlassJarParasiteMaggot extends TileEntity {

	Random rand = new Random();

	public double rotation=rand.nextInt(360);
	public boolean dead;
	public boolean burned;
	
	public TileEntityGlassJarParasiteMaggot(){
	}

	
	@Override
    public void updateEntity() {
		
		if(this.isDead()){
			//System.out.println("DEAD!");
		}
	}


	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("dead", this.dead);
		nbt.setBoolean("burned", this.burned);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.dead=nbt.getBoolean("dead");
		this.burned=nbt.getBoolean("burned");
	}
	
	public boolean isDead() {
		return this.dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
		this.markDirty();
	}


	public boolean isBurned() {
		return this.burned;
	}
	public void setBurned(boolean burned) {
		this.burned = burned;
		this.markDirty();
	}
	
	@Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		NBTTagCompound tagCom = pkt.func_148857_g();
		this.readFromNBT(tagCom);
    }
	
	@Override
    public Packet getDescriptionPacket(){
		NBTTagCompound tagCom = new NBTTagCompound();
		this.writeToNBT(tagCom);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, this.blockMetadata, tagCom);
	}

	

	
}
